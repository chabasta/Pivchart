package cz.cvut.fel.pivchart.service.interfaces

import cz.cvut.fel.pivchart.model.entity.Competition
import cz.cvut.fel.pivchart.model.request.CreateCompetitionRequest
import cz.cvut.fel.pivchart.model.request.UpdateCompetitionRequest

interface CompetitionServiceI {

    /**
     * @return list of competitions
     *
     * Return all competitions
     */
    fun getAll(): MutableList<Competition>

    /**
     * @param competitionId is Id of the competition
     * @return competition
     *
     * Get competition by Id
     */
    fun get(competitionId: Long): Competition

    /**
     * @param request contains attributes from json file to create a competition
     * @return competition
     *
     * Create competition with required parameters after validation
     */
    fun create(request: CreateCompetitionRequest): Competition

    /**
     * @param competitionId is Id of the competition
     * @param request contains attributes from json file to update a competition
     * @return compeition
     *
     * Update current competition parameters after validation
     */
    fun update(competitionId: Long, request: UpdateCompetitionRequest): Competition

    /**
     * @param competitionId is Id of the competition
     *
     * Delete competition with competitionId after validation
     */
    fun delete(competitionId: Long)

    /**
     * @param competitionId is Id of the competition
     * @return competition
     *
     * Finish competition with competitionId
     */
    fun finish(competitionId: Long): Competition
}