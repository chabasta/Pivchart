package cz.cvut.fel.pivchart.service.interfaces

import cz.cvut.fel.pivchart.model.entity.PubDrink
import cz.cvut.fel.pivchart.model.request.CreatePubDrinkRequest
import cz.cvut.fel.pivchart.model.request.UpdatePubDrinkRequest

interface PubDrinkServiceI {

    /**
     * @param pubId is Id of the pub
     * @return list of pub drinks
     *
     * Return all drinks that the pub contains
     */
    fun getAll(pubId: Long) : MutableList<PubDrink>

    /**
     * @param pubId is Id of the pub
     * @param pubDrinkId is Id of the pub drink
     * @return pub drink
     *
     * Get pub drink by pub and drink id
     */
    fun get(pubId: Long, pubDrinkId: Long): PubDrink

    /**
     * @param pubId is Id of the pub
     * @param request contains attributes from json file to create a pub drink
     * @return pub drink
     *
     * Create pub drink after validation
     * and push it to current bar
     */
    fun create(pubId: Long, request: CreatePubDrinkRequest): PubDrink

    /**
     * @param pubId is Id of the pub
     * @param pubDrinkId is Id of the pub drink
     * @param request contains attributes from json file to update a pub drink
     * @return pub drink
     *
     * Update current pub drink after validation
     */
    fun update(pubId: Long, pubDrinkId: Long, request: UpdatePubDrinkRequest): PubDrink


    /**
     * @param pubId is Id of the pub
     * @param pubDrinkId is Id of the pub drink
     *
     * Delete pub drink from the pub
     */
    fun delete(pubId: Long, pubDrinkId: Long)
}