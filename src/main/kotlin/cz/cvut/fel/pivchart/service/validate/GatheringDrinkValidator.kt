package cz.cvut.fel.pivchart.service.validate

import cz.cvut.fel.pivchart.exceptions.api.NotFoundException
import cz.cvut.fel.pivchart.model.request.CreateGatheringDrinkRequest
import cz.cvut.fel.pivchart.repository.GatheringDrinkRepository
import cz.cvut.fel.pivchart.repository.GatheringRepository
import cz.cvut.fel.pivchart.repository.PubDrinkRepository
import cz.cvut.fel.pivchart.repository.PubRepository
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.stereotype.Component

/**
 * Gathering drinks validation
 */
@Component
class GatheringDrinkValidator(
    val gatheringDrinkRepository: GatheringDrinkRepository,
    val pubRepository: PubRepository,
    val pubDrinkRepository: PubDrinkRepository,
    val pubDrinkValidator: PubDrinkValidator,
    val gatheringValidator: GatheringValidator,
    val gatheringRepository: GatheringRepository
) {
    fun validateOnGetAll(gatheringId: Long) {
        gatheringValidator.validateGatheringExists(gatheringId)
    }

    fun validateOnCreate(gatheringId: Long, request: CreateGatheringDrinkRequest) {
        gatheringValidator.validateGatheringExists(gatheringId)
        val gathering = gatheringRepository.getById(gatheringId)
        gatheringValidator.validateIsCurrentlyAuthorizedIsMember(gathering)

        val pub = gathering.pub
        pubDrinkValidator.validatePubDrinkExists(request.pubDrinkId)
        val pubDrink = pubDrinkRepository.getById(request.pubDrinkId)
        pubDrinkValidator.validatePubDrinkBelongsToPub(pub, pubDrink)
    }

    fun validateOnDelete(gatheringId: Long, gatheringDrinkId: Long) {
        gatheringValidator.validateGatheringExists(gatheringId)
        validateGatheringDrinkExists(gatheringDrinkId)
    }

    /**
     * @param gatheringDrinkId is Id of the gathering drink
     *
     * Validate if drink exists in gathering. If it doesn't exist -> Exception
     */
    @Throws(NotFoundException::class)
    fun validateGatheringDrinkExists(gatheringDrinkId: Long) {
        try {
            gatheringDrinkRepository.getById(gatheringDrinkId)
        } catch (ex: JpaObjectRetrievalFailureException) {
            throw NotFoundException("Gathering doesnt exit.")
        }
    }
}