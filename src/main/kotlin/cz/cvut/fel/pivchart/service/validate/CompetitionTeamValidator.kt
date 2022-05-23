package cz.cvut.fel.pivchart.service.validate

import cz.cvut.fel.pivchart.utils.Auth
import cz.cvut.fel.pivchart.exceptions.api.NotFoundException
import cz.cvut.fel.pivchart.exceptions.api.ForbiddenException
import cz.cvut.fel.pivchart.model.entity.Competition
import cz.cvut.fel.pivchart.model.request.UpdateCompetitionTeamCaptainRequest
import cz.cvut.fel.pivchart.model.request.UpdateCompetitionTeamRequest
import cz.cvut.fel.pivchart.repository.CompetitionRepository
import cz.cvut.fel.pivchart.repository.CompetitionTeamRepository
import org.springframework.stereotype.Component

/**
 * Competition team validation
 */
@Component
class CompetitionTeamValidator(
    val competitionValidator: CompetitionValidator,
    val competitionTeamRepository: CompetitionTeamRepository,
    val competitionRepository: CompetitionRepository,
    val auth: Auth
) {

    fun validateOnGet(competitionId: Long) {
        competitionValidator.validateCompetitionExists(competitionId)
    }

    fun validateOnCreate(competitionId: Long) {
        competitionValidator.validateCompetitionExists(competitionId)
        val competition = competitionRepository.getById(competitionId)
        validateIsCurrentlyAuthorizedUserOwner(competition)
    }

    fun validateOnDelete(competitionId: Long) {
        competitionValidator.validateCompetitionExists(competitionId)
        val competition = competitionRepository.getById(competitionId)
        validateIsCurrentlyAuthorizedUserOwner(competition)
    }

    fun validateOnUpdate(competitionId: Long, request: UpdateCompetitionTeamRequest) {
        competitionValidator.validateCompetitionExists(competitionId)
        val competition = competitionRepository.getById(competitionId)
        validateIsCurrentlyAuthorizedUserOwner(competition)
    }

    fun validateOnUpdateCaptain(competitionId: Long, competitionTeamId:Long, request: UpdateCompetitionTeamCaptainRequest) {
        competitionValidator.validateCompetitionExists(competitionId)
        val competition = competitionRepository.getById(competitionId)
        validateIsCurrentlyTeamExists(competitionTeamId)
        validateIsCurrentlyAuthorizedUserOwner(competition)
    }

    /**
     * @param competitionTeamId is Id of the competition team
     *
     * Validate if team exists in competition. If it doesn't exist -> Exception
     */
    @Throws(NotFoundException::class)
    fun validateIsCurrentlyTeamExists(competitionTeamId: Long) {
        try {
            competitionTeamRepository.getById(competitionTeamId)
        } catch (e: NotFoundException) {
            throw NotFoundException("Team not found")
        }
    }

    /**
     * @param competition is current competition
     *
     * Validate if currently authorized user is owner, if not -> Exception
     */
    @Throws(ForbiddenException::class)
    fun validateIsCurrentlyAuthorizedUserOwner(competition: Competition) {
        val authUser = auth.get()

        val isOwner = competition.owner == authUser

        if (!isOwner) throw ForbiddenException("Logged user is not an author of this gathering.")
    }
}