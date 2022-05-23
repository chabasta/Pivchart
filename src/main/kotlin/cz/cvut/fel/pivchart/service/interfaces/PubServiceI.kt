package cz.cvut.fel.pivchart.service.interfaces

import cz.cvut.fel.pivchart.model.entity.Pub
import cz.cvut.fel.pivchart.model.request.CreatePubRequest
import cz.cvut.fel.pivchart.model.request.IndexPubsRequest
import cz.cvut.fel.pivchart.model.request.UpdatePubRequest

interface PubServiceI {

    /**
     * @param request contains json file with attributes
     * @return List of pubs
     *
     * Return all pubs that match attributes
     */
    fun getAll(request: IndexPubsRequest): List<Pub>

    /**
     * @param pubId is Id of the pub
     * @return pub
     *
     * Get pub by Id
     */
    fun get(pubId: Long): Pub

    /**
     * @param request contains attributes from json file to create a pub
     * @return pub
     *
     * Create pub with required parameters after validation
     */
    fun create(request: CreatePubRequest): Pub

    /**
     * @param pubId is Id of the pub
     * @param request contains attributes from json file to update a pub
     * @return pub
     *
     * Update current pub after validation
     */
    fun update(pubId: Long, request: UpdatePubRequest): Pub


    /**
     * @param pubId is Id of the pub
     *
     * Delete pub with pudId after validation
     */
    fun delete(pubId: Long)
}