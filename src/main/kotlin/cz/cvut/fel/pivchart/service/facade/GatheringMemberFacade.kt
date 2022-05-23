package cz.cvut.fel.pivchart.service.facade

import cz.cvut.fel.pivchart.utils.GatheringUtils
import cz.cvut.fel.pivchart.utils.UserUtils
import cz.cvut.fel.pivchart.model.entity.GatheringMember
import cz.cvut.fel.pivchart.model.request.CreateGatheringMemberRequest
import cz.cvut.fel.pivchart.model.response.GatheringMemberResponse
import cz.cvut.fel.pivchart.service.interfaces.GatheringMemberServiceI
import cz.cvut.fel.pivchart.service.validate.GatheringMemberValidator
import org.springframework.stereotype.Service
import java.util.stream.Collectors

/**
 * Gathering member service facade
 */
@Service
class GatheringMemberFacade(
    private val gatheringMemberService: GatheringMemberServiceI,
    private val gatheringMemberValidator: GatheringMemberValidator
) {
    fun getAll(gatheringId: Long): MutableList<GatheringMemberResponse> {
        gatheringMemberValidator.validateOnGetAll(gatheringId)
        val gatheringMember = gatheringMemberService.getAll(gatheringId)

        return gatheringMember
            .stream()
            .map { prepareGatheringMemberResponse(it) }
            .collect(Collectors.toList())
    }

    fun create(gatheringId: Long, request: CreateGatheringMemberRequest): GatheringMemberResponse {
        gatheringMemberValidator.validateOnCreate(gatheringId, request)
        val gatheringMember = gatheringMemberService.create(gatheringId, request)

        return prepareGatheringMemberResponse(gatheringMember)
    }

    fun delete(gatheringId: Long, gatheringMemberId: Long) {
        gatheringMemberValidator.validateOnDelete(gatheringId)
        gatheringMemberService.delete(gatheringId, gatheringMemberId)
    }

    private fun prepareGatheringMemberResponse(gatheringMember: GatheringMember): GatheringMemberResponse {
        return GatheringMemberResponse(
                gathering = GatheringUtils.prepareGatheringResponse(gatheringMember.gathering),
                user = UserUtils.prepareUserResponse(gatheringMember.user),
                isOwner = gatheringMember.isOwner
        )
    }
}