package cz.cvut.fel.pivchart.service

import cz.cvut.fel.pivchart.utils.Auth
import cz.cvut.fel.pivchart.exceptions.api.BadRequestException
import cz.cvut.fel.pivchart.model.entity.Competition
import cz.cvut.fel.pivchart.model.entity.CompetitionTeam
import cz.cvut.fel.pivchart.model.entity.CompetitionTeamMember
import cz.cvut.fel.pivchart.model.entity.enum.CompetitionEndType
import cz.cvut.fel.pivchart.model.request.CreateCompetitionRequest
import cz.cvut.fel.pivchart.model.request.UpdateCompetitionRequest
import cz.cvut.fel.pivchart.repository.*
import cz.cvut.fel.pivchart.service.interfaces.CompetitionServiceI
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import javax.transaction.Transactional

/**
 * Competition service
 */
@Service
class CompetitionService(
    private val competitionRepository: CompetitionRepository,
    private val competitionTeamRepository: CompetitionTeamRepository,
    private val pubRepository: PubRepository,
    private val userRepository: UserRepository,
    private val competitionTeamMemberRepository: CompetitionTeamMemberRepository,
    private val auth: Auth
): CompetitionServiceI {

    @Transactional
    override fun getAll(): MutableList<Competition> {
        return auth.get().competitionTeamMembers.stream().map{
            it.competitionTeam.competition
        }.collect(Collectors.toList())
    }

    @Transactional
    override fun get(competitionId: Long): Competition {
        return competitionRepository.getById(competitionId)
    }

    @Transactional
    override fun create(request: CreateCompetitionRequest): Competition {

        val authUser = auth.get()

        var competition = Competition().apply {
            this.title = request.title
            this.owner = authUser
            this.startDateTime = request.startDateTime
            this.endDateTime = request.endDateTime
            this.pub = pubRepository.getById(request.pubId)
            this.amount = request.amount
            this.competitionEndType = request.endType
        }
        competition = competitionRepository.save(competition)

        val competitionTeams: MutableList<CompetitionTeam> = ArrayList()
        for (req in request.teams) {
            //create team
            var team = CompetitionTeam().apply {
                this.title = req.title
                this.competition = competition
            }
            team = competitionTeamRepository.save(team)
            //create captain
            var member = CompetitionTeamMember().apply {
                this.user = userRepository.getById(req.captainId)
                this.isCaptain = true
                this.competitionTeam = team
            }

            member = competitionTeamMemberRepository.save(member)
            authUser.competitionTeamMembers.add(member)
            team.competitionTeamMembers.add(member)
            userRepository.save(authUser)

            team = competitionTeamRepository.save(team)
            //add team to team list
            competitionTeams.add(team)

        }

        competition.apply {
            this.competitionTeams = competitionTeams
        }

        return competitionRepository.save(competition)

    }

    @Transactional
    override fun update(competitionId: Long, request: UpdateCompetitionRequest): Competition {

        val competition = competitionRepository.getById(competitionId)
        competition.apply {
            this.title = request.title
        }

        return competitionRepository.save(competition)

    }

    @Transactional
    override fun delete(competitionId: Long) {

        val toDelete = competitionRepository.findById(competitionId).get()
        toDelete.competitionTeams.forEach {
            it.competitionTeamMembers.forEach{
                //it.competitionTeam.competitionTeamMembers.remove(it)
                it.user.competitionTeamMembers.remove(it)
                userRepository.save(it.user)
                competitionTeamMemberRepository.delete(it)
            }
            competitionTeamRepository.delete(it)
        }

        competitionRepository.delete(toDelete)

    }

    @Transactional
    override fun finish(competitionId: Long): Competition {

        var competition = competitionRepository.getById(competitionId)

        if (competition.isFinished) throw BadRequestException("Unable to edit finished competition")

        if (competition.competitionEndType == CompetitionEndType.LEADER_STOPS) {
            competition = competition.apply {
                this.isFinished = true
            }
        } else {
            throw BadRequestException("You have no rights to stop competition")
        }
        
        return competitionRepository.save(competition)
    }
}