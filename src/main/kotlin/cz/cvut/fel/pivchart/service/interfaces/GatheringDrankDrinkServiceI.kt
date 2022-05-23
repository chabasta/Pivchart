package cz.cvut.fel.pivchart.service.interfaces

import cz.cvut.fel.pivchart.model.entity.GatheringDrankDrink
import cz.cvut.fel.pivchart.model.request.CreateGatheringDrinkRequest

interface GatheringDrankDrinkServiceI {

    /**
     * @param gatheringId is Id of the gathering
     * @return list of gathering drinks
     *
     * Return all drinks from gathering with gatheringId
     */
    fun getAll(gatheringId: Long): MutableList<GatheringDrankDrink>

    /**
     * @param gatheringId is Id of the gathering
     * @param request contains attributes from json file to create a gathering drink
     *
     * Create gathering drink with required parameters after validation
     * and push it to current gathering
     */
    fun create(gatheringId: Long, request: CreateGatheringDrinkRequest): GatheringDrankDrink

    /**
     * @param gatheringId is Id of the gathering
     * @param gatheringDrinkId is Id of the gathering drink
     *
     * Delete current gathering drink from gathering after validation
     */
    fun delete(gatheringId: Long, gatheringDrinkId: Long)
}