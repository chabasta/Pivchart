package cz.cvut.fel.pivchart.service.validate

import cz.cvut.fel.pivchart.utils.Auth
import cz.cvut.fel.pivchart.exceptions.api.NotFoundException
import cz.cvut.fel.pivchart.exceptions.api.ForbiddenException
import cz.cvut.fel.pivchart.model.entity.Competition
import cz.cvut.fel.pivchart.model.request.CreateCompetitionRequest
import cz.cvut.fel.pivchart.model.request.UpdateCompetitionRequest
import cz.cvut.fel.pivchart.repository.CompetitionRepository
import org.springframework.stereotype.Component

/**
 * Competition validation
 */
@Component
class CompetitionValidator(
    val competitionRepository: CompetitionRepository,
    val pubValidator: PubValidator,
    val auth: Auth
) {
    fun validateOnGet(competitionId: Long) {
        validateCompetitionExists(competitionId)
    }

    fun validateOnCreate(request: CreateCompetitionRequest) {
        pubValidator.validatePubExists(request.pubId)
    }

    fun validateOnUpdate(competitionId: Long, request: UpdateCompetitionRequest) {
        validateCompetitionExists(competitionId)
        val competition = competitionRepository.getById(competitionId)
        validateIsCurrentlyAuthorizedIsAuthor(competition)
    }

    fun validateOnDelete(competitionId: Long) {
        validateCompetitionExists(competitionId)
        val competition = competitionRepository.getById(competitionId)
        validateIsCurrentlyAuthorizedIsAuthor(competition)
    }

    fun validateOnFinish(competitionId: Long) {
        validateCompetitionExists(competitionId)
        val competition = competitionRepository.getById(competitionId)
        validateIsCurrentlyAuthorizedIsAuthor(competition)
    }

    /**
     * @param competitionId is Id of the competition
     *
     * Validate if competition exists. If it doesn't exist -> Exception
     */
    @Throws(NotFoundException::class)
    fun validateCompetitionExists(competitionId: Long){
        if (!competitionRepository.existsById(competitionId)) throw NotFoundException("Competition doesnt exit.")
    }

    /**
     * @param competition is current competition
     *
     * Validate if currently authorized user is author of this competition,
     * if not -> Exception
     */
    @Throws(ForbiddenException::class)
    fun validateIsCurrentlyAuthorizedIsAuthor(competition: Competition) {
        val authUser = auth.get()

        val isOwner = competition.owner == authUser

        if (!isOwner) throw ForbiddenException("Logged user is not an author of this competition.")
    }

    /**
     * @param competition is current competition
     *
     * Validate if currently authorized user is member of this competition,
     * if not -> Exception
     */
    @Throws(ForbiddenException::class)
    fun validateIsCurrentlyAuthorizedIsMember(competition: Competition) {
        val authUser = auth.get()

        val isOwner = competition.competitionTeams.stream()
            .anyMatch { it.competitionTeamMembers.stream()
                .anyMatch { teamMember -> teamMember.user == authUser }
            }

        if (!isOwner) throw ForbiddenException("Logged user is not a member of this competition.")
    }

    /**
     * @param competition is current competition
     *
     * Validate if currently authorized user is captain of this competition,
     * if not -> Exception
     */
    @Throws(ForbiddenException::class)
    fun validateIsCurrentlyAuthorizedIsCaptain(competition: Competition) {
        val authUser = auth.get()

        val isCaptain = competition.competitionTeams.stream()
            .anyMatch{ it.competitionTeamMembers.stream()
                    .anyMatch { teamMember -> teamMember.user == authUser && teamMember.isCaptain }
            }

        if (!isCaptain) throw ForbiddenException("Logged user is not a captain in this competition.")
    }
}