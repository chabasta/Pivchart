package cz.cvut.fel.pivchart.service.validate

import cz.cvut.fel.pivchart.exceptions.api.NotFoundException
import cz.cvut.fel.pivchart.model.request.CreateCompetitionDrinkRequest
import cz.cvut.fel.pivchart.repository.CompetitionDrinkRepository
import cz.cvut.fel.pivchart.repository.CompetitionRepository
import cz.cvut.fel.pivchart.repository.PubDrinkRepository
import cz.cvut.fel.pivchart.repository.PubRepository
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.stereotype.Component

/**
 * Competition drank drink validation
 */
@Component
class CompetitionDrinkValidator(
    val competitionDrinkRepository: CompetitionDrinkRepository,
    val competitionRepository: CompetitionRepository,
    val pubRepository: PubRepository,
    val pubDrinkRepository: PubDrinkRepository,
    val pubDrinkValidator: PubDrinkValidator,
    val competitionValidator: CompetitionValidator
) {

    fun validateOnGetAll(competitionId: Long) {
        competitionValidator.validateCompetitionExists(competitionId)
    }

    fun  validateOnCreate(competitionId: Long, request: CreateCompetitionDrinkRequest) {
        competitionValidator.validateCompetitionExists(competitionId)
        val competition = competitionRepository.getById(competitionId)
        competitionValidator.validateIsCurrentlyAuthorizedIsMember(competition)

        val pub  = competition.pub
        pubDrinkValidator.validatePubDrinkExists(request.pubDrinkId)
        val pubDrink = pubDrinkRepository.getById(request.pubDrinkId)
        pubDrinkValidator.validatePubDrinkBelongsToPub(pub, pubDrink)
    }

    fun validateOnDelete(competitionId: Long, competitionDrinkId: Long) {
        competitionValidator.validateCompetitionExists(competitionId)
        validateCompetitionDrinkExists(competitionDrinkId)
    }

    /**
     * @param competitionDrinkId is Id of the competition drank drink
     *
     * Validate if drink exists in competition. If it doesn't exist -> Exception
     */
    @Throws(NotFoundException::class)
    fun validateCompetitionDrinkExists(competitionDrinkId: Long) {
        if (!competitionDrinkRepository.existsById(competitionDrinkId)) throw NotFoundException("Drink not exists.")
    }
}