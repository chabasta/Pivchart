package cz.cvut.fel.pivchart.service.interfaces

import cz.cvut.fel.pivchart.model.entity.Drink
import cz.cvut.fel.pivchart.model.request.CreateDrinkRequest
import cz.cvut.fel.pivchart.model.request.UpdateDrinkRequest

interface DrinkServiceI {

    /**
     * @return list of drinks
     *
     * Get all drinks that exist
     */
    fun getAll(): MutableList<Drink>

    /**
     * @param id is Id of the drink
     * @return drink
     *
     * Get drink by Id
     */
    fun get(id: Long): Drink

    /**
     * @param request contains attributes from json file to create a drink
     * @return drink
     *
     * Create drink with required parameters after validation
     */
    fun create(request: CreateDrinkRequest): Drink

    /**
     * @param id is Id of the drink
     * @param contains attributes from json file to update a drink
     * @return drink
     *
     * Update current drink parameters after validation
     */
    fun update(id: Long, request: UpdateDrinkRequest): Drink

    /**
     * @param id is Id of the drink
     *
     * Delete drink with id after validation
     */
    fun delete(id: Long)
}