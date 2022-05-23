package cz.cvut.fel.pivchart.service

import cz.cvut.fel.pivchart.exceptions.api.BadRequestException
import cz.cvut.fel.pivchart.model.entity.CompetitionTeam
import cz.cvut.fel.pivchart.model.entity.CompetitionTeamMember
import cz.cvut.fel.pivchart.model.entity.GatheringMember
import cz.cvut.fel.pivchart.model.entity.User
import cz.cvut.fel.pivchart.model.request.TeamRequest
import cz.cvut.fel.pivchart.model.request.UpdateCompetitionTeamCaptainRequest
import cz.cvut.fel.pivchart.model.request.UpdateCompetitionTeamRequest
import cz.cvut.fel.pivchart.repository.CompetitionRepository
import cz.cvut.fel.pivchart.repository.CompetitionTeamMemberRepository
import cz.cvut.fel.pivchart.repository.CompetitionTeamRepository
import cz.cvut.fel.pivchart.repository.UserRepository
import cz.cvut.fel.pivchart.service.interfaces.CompetitionTeamServiceI
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import javax.transaction.Transactional

/**
 * Competition team service
 */
@Service
class CompetitionTeamService (
    private val competitionRepository: CompetitionRepository,
    private val competitionTeamRepository: CompetitionTeamRepository,
    private val competitionTeamMemberRepository: CompetitionTeamMemberRepository,
    private val userRepository: UserRepository
): CompetitionTeamServiceI {

    @Transactional
    override fun getAll(competitionId: Long): MutableList<CompetitionTeam> {
        return competitionRepository.getById(competitionId).competitionTeams
    }

    @Transactional
    override fun create(competitionId: Long, request: TeamRequest): CompetitionTeam {

        val competition = competitionRepository.getById(competitionId)

        if (competition.isFinished) throwAlreadyFinished()

        val competitionTeam = CompetitionTeam().apply {
            this.title = request.title
            this.competition = competition
        }

        competitionTeamRepository.save(competitionTeam)

        val newCompetitionTeamMember = CompetitionTeamMember().apply{
            this.user = userRepository.getById(request.captainId)
            this.isCaptain = true
            this.competitionTeam = competitionTeam
        }

        competitionTeamMemberRepository.save(newCompetitionTeamMember)
        competitionTeam.competitionTeamMembers.add(newCompetitionTeamMember)
        competitionTeamRepository.save(competitionTeam)
        competitionRepository.save(competition)

        return competitionTeam
    }

    @Transactional
    override fun delete(competitionId: Long, competitionTeamId: Long) {

        val competition = competitionRepository.getById(competitionId)

        if (competition.isFinished) throwAlreadyFinished()

        val competitionTeamToDelete = competitionTeamRepository.getById(competitionTeamId)

        competitionTeamToDelete.competitionTeamMembers.forEach{
            it.user.competitionTeamMembers.remove(it)
            userRepository.save(it.user)
            competitionTeamMemberRepository.delete(it)
        }

        competitionTeamRepository.delete(competitionTeamToDelete)
        competitionRepository.save(competition)
    }

    @Transactional
    override fun update(competitionId: Long, competitionTeamId: Long, request: UpdateCompetitionTeamRequest): CompetitionTeam {

        val competition = competitionRepository.getById(competitionId)

        if (competition.isFinished) throwAlreadyFinished()

        var competitionTeam = competitionTeamRepository.getById(competitionTeamId)

        val requestMembers = userRepository.findAllById(request.membersIds)
        val currentMembers = competitionTeam.competitionTeamMembers

        competitionTeam.apply {
            this.title = request.title
        }


        val todelete: MutableList<CompetitionTeamMember> = currentMembers.stream().filter{ !requestMembers.contains(it.user) && !it.isCaptain}.collect(Collectors.toList())

        todelete.forEach{
            //remove user
            competitionTeam.competitionTeamMembers.remove(it)
            it.user.competitionTeamMembers.remove(it)
            userRepository.save(it.user)
            competitionTeamMemberRepository.delete(it)

        }

        val toAdd: MutableList<User> = requestMembers.stream().filter{ requestUser ->  currentMembers.stream().map { it.user }.noneMatch{ currentUser -> currentUser.id == requestUser.id } }.collect(Collectors.toList())

        toAdd.forEach {

            //add request user
            val newCompetitionTeamMember = CompetitionTeamMember().apply{
                this.user = it
                this.competitionTeam = competitionTeam
            }
            competitionTeamMemberRepository.save(newCompetitionTeamMember)
            competitionTeam.competitionTeamMembers.add(newCompetitionTeamMember)

        }

        competitionTeamRepository.save(competitionTeam)
        competitionRepository.save(competition)

        return competitionTeam
    }

    @Transactional
    override fun updateCaptain(competitionId: Long, competitionTeamId: Long, request: UpdateCompetitionTeamCaptainRequest): CompetitionTeam {

        val competition = competitionRepository.getById(competitionId)

        if (competition.isFinished) throwAlreadyFinished()

        val competitionTeam = competitionTeamRepository.getById(competitionTeamId)

        val existMember = competitionTeam.competitionTeamMembers.stream().anyMatch{ it.user.id == request.captainId}
        val requestCaptain = userRepository.getById(request.captainId)

        if (!existMember) throw BadRequestException("This user is not a member of the team")

        for (competitionTeamMember in competitionTeam.competitionTeamMembers){
            if (competitionTeamMember.user.id != requestCaptain.id && competitionTeamMember.isCaptain){
                competitionTeamMember.apply {
                    this.isCaptain = false
                }
                competitionTeamMemberRepository.save(competitionTeamMember)

            } else if (competitionTeamMember.user.id == requestCaptain.id){
                competitionTeamMember.apply {
                    this.isCaptain = true
                }

                competitionTeamMemberRepository.save(competitionTeamMember)
            }
        }

        competitionTeamRepository.save(competitionTeam)

        return competitionTeam
    }

    private fun throwAlreadyFinished() {
        throw BadRequestException("Competition is already finished (closed)")
    }
}