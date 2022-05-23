package cz.cvut.fel.pivchart.service.facade

import cz.cvut.fel.pivchart.utils.CompetitionMemberUtils.prepareMemberResponse
import cz.cvut.fel.pivchart.model.entity.CompetitionTeam
import cz.cvut.fel.pivchart.model.request.TeamRequest
import cz.cvut.fel.pivchart.model.request.UpdateCompetitionTeamCaptainRequest
import cz.cvut.fel.pivchart.model.request.UpdateCompetitionTeamRequest
import cz.cvut.fel.pivchart.model.response.CompetitionTeamResponse
import cz.cvut.fel.pivchart.service.interfaces.CompetitionTeamServiceI
import cz.cvut.fel.pivchart.service.validate.CompetitionTeamValidator
import org.springframework.stereotype.Service
import java.util.stream.Collectors

/**
 * Competition team service facade
 */
@Service
class CompetitionTeamFacade (
    private val competitionTeamService: CompetitionTeamServiceI,
    private val competitionTeamValidator: CompetitionTeamValidator,
){
    fun getAll(competitionId: Long): MutableList<CompetitionTeamResponse> {
        competitionTeamValidator.validateOnGet(competitionId)
        val competitionTeams = competitionTeamService.getAll(competitionId)
        return competitionTeams.stream()
                .map {prepareResponse(it)}
                .collect(Collectors.toList())
    }

    fun create(competitionId: Long, request: TeamRequest): CompetitionTeamResponse {
        competitionTeamValidator.validateOnCreate(competitionId)
        val competitionTeam = competitionTeamService.create(competitionId, request)
        return prepareResponse(competitionTeam)
    }

    fun update(competitionId: Long, competitionTeamId: Long, request: UpdateCompetitionTeamRequest): CompetitionTeamResponse{
        competitionTeamValidator.validateOnUpdate(competitionId, request)
        val competitionTeam = competitionTeamService.update(competitionId,competitionTeamId, request)
        return prepareResponse(competitionTeam)
    }

    fun updateCaptain(competitionId: Long, competitionTeamId: Long, request: UpdateCompetitionTeamCaptainRequest): CompetitionTeamResponse{
        competitionTeamValidator.validateOnUpdateCaptain(competitionId, competitionTeamId ,request)
        val competitionTeam = competitionTeamService.updateCaptain(competitionId, competitionTeamId, request)
        return prepareResponse(competitionTeam)
    }

    fun delete(competitionId: Long, competitionTeamId: Long) {
        competitionTeamValidator.validateOnDelete(competitionId)
        competitionTeamService.delete(competitionId, competitionTeamId)
    }

    private fun prepareResponse(competitionTeam: CompetitionTeam): CompetitionTeamResponse {

        return CompetitionTeamResponse(
                id = competitionTeam.id!!,
                title = competitionTeam.title,
                competitionId = competitionTeam.competition.id!!,
                members = competitionTeam.competitionTeamMembers.map{ prepareMemberResponse(it) }
        )
    }
}