package cz.cvut.fel.pivchart.service.interfaces

import cz.cvut.fel.pivchart.model.entity.Gathering
import cz.cvut.fel.pivchart.model.request.CreateGatheringRequest
import cz.cvut.fel.pivchart.model.request.UpdateGatheringRequest

interface GatheringServiceI {

    /**
     * @return list of gatherings
     *
     * Return all gatherings
     */
    fun getAll(): MutableList<Gathering>

    /**
     * @param gatheringId is Id of the gathering
     * @return gathering
     *
     * Get gathering by id
     */
    fun get(gatheringId: Long): Gathering

    /**
     * @param request contains attributes from json file to create a gathering
     * @return gathering
     *
     * Create gathering with required parameters after validation
     */
    fun create(request: CreateGatheringRequest): Gathering

    /**
     * @param gatheringId is Id of the gathering
     * @param request contains attributes from json file to update a gathering
     * @return gathering
     *
     * Update current gathering parameters after validation
     */
    fun update(gatheringId: Long, request: UpdateGatheringRequest): Gathering

    /**
     * @param gatheringId is Id of the gathering
     *
     * Delete gathering with gatheringId after validation
     */
    fun delete(gatheringId: Long)

    /**
     * @param gatheringId is Id of the gathering
     * @return gathering
     *
     * Finish current gathering
     */
    fun finish(gatheringId: Long): Gathering
}