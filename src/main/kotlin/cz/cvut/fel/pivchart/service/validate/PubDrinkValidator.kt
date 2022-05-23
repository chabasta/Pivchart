package cz.cvut.fel.pivchart.service.validate

import cz.cvut.fel.pivchart.exceptions.api.BadRequestException
import cz.cvut.fel.pivchart.exceptions.api.NotFoundException
import cz.cvut.fel.pivchart.model.entity.Pub
import cz.cvut.fel.pivchart.model.entity.PubDrink
import cz.cvut.fel.pivchart.model.request.CreatePubDrinkRequest
import cz.cvut.fel.pivchart.model.request.UpdatePubDrinkRequest
import cz.cvut.fel.pivchart.repository.PubDrinkRepository
import cz.cvut.fel.pivchart.repository.PubRepository
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.stereotype.Component

/**
 * Pub drinks validator
 */
@Component
class PubDrinkValidator(
    val pubRepository: PubRepository,
    val pubDrinkRepository: PubDrinkRepository,
    val pubValidator: PubValidator,
    val drinkValidator: DrinkValidator
) {
    fun validateOnGet(pubId: Long, pubDrinkId: Long) {
        validatePubExists(pubId)
        validatePubDrinkExists(pubDrinkId)
        validatePubDrinkBelongsToPub(
            pubRepository.getById(pubId),
            pubDrinkRepository.getById(pubDrinkId)
        )
    }

    fun validateOnGetAll(pubId: Long) {
        validatePubExists(pubId)
    }

    fun validateOnCreate(pubId: Long, request: CreatePubDrinkRequest) {
        validatePubExists(pubId)
        drinkValidator.validateDrinkExists(request.drinkId)
    }

    fun validateOnUpdate(pubId: Long, pubDrinkId: Long, request: UpdatePubDrinkRequest) {
        validatePubExists(pubId)
        validatePubDrinkExists(pubDrinkId)
        validatePubDrinkBelongsToPub(
            pubRepository.getById(pubId),
            pubDrinkRepository.getById(pubDrinkId)
        )
    }

    fun validateOnDelete(pubId: Long, pubDrinkId: Long) {
        validatePubExists(pubId)
        validatePubDrinkExists(pubDrinkId)
        validatePubDrinkBelongsToPub(
            pubRepository.getById(pubId),
            pubDrinkRepository.getById(pubDrinkId)
        )
    }

    private fun validatePubExists(pubId: Long) {
        pubValidator.validatePubExists(pubId)
    }

    /**
     * @param pub is current pub
     * @param pubDrink is pub drink
     *
     * Validate if drink belongs to current pub. If it doesn't belong -> Exception
     */
    @Throws(BadRequestException::class)
    fun validatePubDrinkBelongsToPub(pub: Pub, pubDrink: PubDrink) {
        if (!pub.pubDrinks.contains(pubDrink)) {
            throw BadRequestException("Drink doest not belong to this pub.")
        }
    }

    private fun validateDrinkExists(drinkId: Long){
        drinkValidator.validateDrinkExists(drinkId)
    }

    /**
     * @param pubDrinkId is Id of the pub drink
     *
     * Validate if pub drink exists. If it doesn't exits -> Exception
     */
    @Throws(NotFoundException::class)
    fun validatePubDrinkExists(pubDrinkId: Long){
        if (!pubDrinkRepository.existsById(pubDrinkId)) throw NotFoundException("Pub drink doesnt exit.")
    }
}