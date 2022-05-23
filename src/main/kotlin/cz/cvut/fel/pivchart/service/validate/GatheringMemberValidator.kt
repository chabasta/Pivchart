package cz.cvut.fel.pivchart.service.validate

import cz.cvut.fel.pivchart.utils.Auth
import cz.cvut.fel.pivchart.model.request.CreateGatheringMemberRequest
import cz.cvut.fel.pivchart.repository.GatheringRepository
import org.springframework.stereotype.Component

/**
 * Gathering member validation
 */
@Component
class GatheringMemberValidator(
    val gatheringValidator: GatheringValidator,
    val gatheringRepository: GatheringRepository,
    val auth: Auth
) {
    fun validateOnGetAll(gatheringId: Long) {
        gatheringValidator.validateGatheringExists(gatheringId)
    }

    fun validateOnCreate(gatheringId: Long, request: CreateGatheringMemberRequest) {
        gatheringValidator.validateGatheringExists(gatheringId)
        val gathering = gatheringRepository.getById(gatheringId)
        gatheringValidator.validateIsCurrentlyAuthorizedUserOwner(gathering)
    }

    fun validateOnDelete(gatheringId: Long) {
        gatheringValidator.validateGatheringExists(gatheringId)
        val gathering = gatheringRepository.getById(gatheringId)
        gatheringValidator.validateIsCurrentlyAuthorizedUserOwner(gathering)
    }
}