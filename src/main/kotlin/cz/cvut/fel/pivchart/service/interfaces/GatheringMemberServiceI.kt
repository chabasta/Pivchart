package cz.cvut.fel.pivchart.service.interfaces

import cz.cvut.fel.pivchart.model.entity.GatheringMember
import cz.cvut.fel.pivchart.model.request.CreateGatheringMemberRequest

interface GatheringMemberServiceI {

    /**
     * @param gatheringId is Id of the gathering
     * @return list of gathering members
     *
     * Return all members from gathering
     */
    fun getAll(gatheringId: Long): MutableList<GatheringMember>

    /**
     * @param gatheringId is Id of the gathering
     * @param request contains attributes from json file to create a gathering
     * @return gathering member
     *
     * Create gathering member with required parameters after validation
     * and push it to current gathering
     */
    fun create(gatheringId: Long, request: CreateGatheringMemberRequest): GatheringMember

    /**
     * @param gatheringId is Id of the gathering
     * @param gatheringMemberId is Id of gathering member
     *
     * Delete member from gathering after validation
     */
    fun delete(gatheringId: Long, gatheringMemberId: Long)
}