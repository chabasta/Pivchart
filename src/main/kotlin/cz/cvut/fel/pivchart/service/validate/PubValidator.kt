package cz.cvut.fel.pivchart.service.validate

import cz.cvut.fel.pivchart.exceptions.api.NotFoundException
import cz.cvut.fel.pivchart.model.request.CreatePubRequest
import cz.cvut.fel.pivchart.model.request.UpdatePubRequest
import cz.cvut.fel.pivchart.repository.PubRepository
import org.springframework.stereotype.Component

/**
 * Pub validation
 */
@Component
class PubValidator(
    val pubRepository: PubRepository
) {
    fun validateOnGetAll() {
        // nothing to check
    }

    fun validateOnGet(pubId: Long) {
        validatePubExists(pubId)
    }

    fun validateOnCreate(request: CreatePubRequest) {
        // nothing to check
    }

    fun validateOnUpdate(pubId: Long, request: UpdatePubRequest) {
        validatePubExists(pubId)
    }

    /**
     * @param pubId is Id of the pub
     *
     * Validate if pub exits. If it doesn't exist -> Exception
     */
    @Throws(NotFoundException::class)
    fun validatePubExists(pubId: Long) {
        if (!pubRepository.existsById(pubId)) throw NotFoundException("Pub doesnt exit.")
    }
}