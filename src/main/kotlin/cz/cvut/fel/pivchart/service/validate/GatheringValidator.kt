package cz.cvut.fel.pivchart.service.validate

import cz.cvut.fel.pivchart.utils.Auth
import cz.cvut.fel.pivchart.exceptions.api.NotFoundException
import cz.cvut.fel.pivchart.exceptions.api.ForbiddenException
import cz.cvut.fel.pivchart.model.entity.Gathering
import cz.cvut.fel.pivchart.model.request.CreateGatheringRequest
import cz.cvut.fel.pivchart.model.request.UpdateGatheringRequest
import cz.cvut.fel.pivchart.repository.GatheringRepository
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.stereotype.Component

/**
 * Gathering validation
 */
@Component
class GatheringValidator(
    val gatheringRepository: GatheringRepository,
    val pubValidator: PubValidator,
    val auth: Auth
) {
    fun validateOnGet(gatheringId: Long) {
        validateGatheringExists(gatheringId)
    }

    fun validateOnCreate(request: CreateGatheringRequest) {
        pubValidator.validatePubExists(request.pubId)
    }

    fun validateOnUpdate(gatheringId: Long, request: UpdateGatheringRequest) {
        validateGatheringExists(gatheringId)
        val gathering = gatheringRepository.getById(gatheringId)
        validateIsCurrentlyAuthorizedUserOwner(gathering)
    }

    fun validateOnDelete(gatheringId: Long) {
        validateGatheringExists(gatheringId)
        val gathering = gatheringRepository.getById(gatheringId)
        validateIsCurrentlyAuthorizedUserOwner(gathering)
    }

    /**
     * @param gatheringId is Id of the gathering
     *
     * Validate if gathering exists. If it doesn't exist -> Exception
     */
    @Throws(NotFoundException::class)
    fun validateGatheringExists(gatheringId: Long){
        if (!gatheringRepository.existsById(gatheringId)) throw NotFoundException("Gathering doesnt exit.")
    }

    /**
     * @param gathering is current gathering
     *
     * Validate if currently authorized user is owner of this gathering,
     * if not -> Exception
     */
    @Throws(ForbiddenException::class)
    fun validateIsCurrentlyAuthorizedUserOwner(gathering: Gathering) {
        val authUser = auth.get()

        val isOwner = gathering.members.stream()
            .anyMatch{
                it.user == authUser && it.isOwner
            }

        if (!isOwner) throw ForbiddenException("Logged user is not an author of this gathering.")
    }

    /**
     * @param gathering is current gathering
     *
     * Validate if currently authorized user is member of this gathering,
     * if not -> Exception
     */
    @Throws(ForbiddenException::class)
    fun validateIsCurrentlyAuthorizedIsMember(gathering: Gathering) {
        val authUser = auth.get()

        val isOwner = gathering.members.stream()
            .anyMatch{
                it.user == authUser
            }

        if (!isOwner) throw ForbiddenException("Logged user is not a member of this gathering.")
    }

    fun validateOnFinish(gatheringId: Long) {
        validateGatheringExists(gatheringId)
        val gathering = gatheringRepository.getById(gatheringId)
        validateIsCurrentlyAuthorizedUserOwner(gathering)
    }
}