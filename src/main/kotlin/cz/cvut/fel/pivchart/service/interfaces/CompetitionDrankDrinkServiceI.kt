package cz.cvut.fel.pivchart.service.interfaces

import cz.cvut.fel.pivchart.model.entity.CompetitionDrankDrink
import cz.cvut.fel.pivchart.model.request.CreateCompetitionDrinkRequest

interface CompetitionDrankDrinkServiceI {

    /**
     * @param competitionId is Id of the competition
     * @return list of competition drank drinks
     *
     * Return all current competition drank drinks
     */
    fun getAll(competitionId: Long): MutableList<CompetitionDrankDrink>

    /**
     * @param competitionId is Id of the competition
     * @param request contains attributes from json file to create a competition drink
     * @return competition drank drink
     *
     * Create competition drank drink after validation
     * and push it to current competition
     */
    fun create(competitionId: Long, request: CreateCompetitionDrinkRequest): CompetitionDrankDrink

    /**
     * @param competitionId is Id of the competition
     * @param competitionDrinkId is Id of the competition drank drink
     *
     * Delete drink from current competition after validation
     */
    fun delete(competitionId: Long, competitionDrinkId: Long)
}