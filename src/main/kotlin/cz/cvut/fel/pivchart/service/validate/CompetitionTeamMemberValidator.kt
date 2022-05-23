package cz.cvut.fel.pivchart.service.validate

import cz.cvut.fel.pivchart.utils.Auth
import cz.cvut.fel.pivchart.model.request.CreateCompetitionTeamMemberRequest
import cz.cvut.fel.pivchart.repository.CompetitionRepository
import org.springframework.stereotype.Component

/**
 * Competition member validation
 */
@Component
class CompetitionTeamMemberValidator(
    val competitionValidator: CompetitionValidator,
    val competitionRepository: CompetitionRepository,
    val auth: Auth
) {
    fun validateOnGetAll(competitionId: Long) {
        competitionValidator.validateCompetitionExists(competitionId)
    }

    fun validateOnCreate(competitionId: Long, request: CreateCompetitionTeamMemberRequest) {
        competitionValidator.validateCompetitionExists(competitionId)
        val competition = competitionRepository.getById(competitionId)
        competitionValidator.validateIsCurrentlyAuthorizedIsCaptain(competition)
    }

    fun validateOnDelete(competitionId: Long) {
        competitionValidator.validateCompetitionExists(competitionId)
        val competition = competitionRepository.getById(competitionId)
        competitionValidator.validateIsCurrentlyAuthorizedIsCaptain(competition)
    }

}