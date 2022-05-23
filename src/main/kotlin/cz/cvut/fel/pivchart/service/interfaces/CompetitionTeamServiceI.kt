package cz.cvut.fel.pivchart.service.interfaces

import cz.cvut.fel.pivchart.model.entity.CompetitionTeam
import cz.cvut.fel.pivchart.model.request.TeamRequest
import cz.cvut.fel.pivchart.model.request.UpdateCompetitionTeamCaptainRequest
import cz.cvut.fel.pivchart.model.request.UpdateCompetitionTeamRequest

interface CompetitionTeamServiceI {

    /**
     * @param competitionId is Id of the competition
     * @return list of competition teams
     *
     * Return all competition teams
     */
    fun getAll(competitionId: Long): MutableList<CompetitionTeam>

    /**
     * @param competitionId is Id of the competition
     * @param request contains attributes from json file to create a competition team
     * @return competition team
     *
     * Create competition team with required parameters after validation
     * and push it to current competition
     */
    fun create(competitionId: Long, request: TeamRequest): CompetitionTeam

    /**
     * @param competitionId is Id of the competition
     * @param competitionTeamId is Id of the competition team
     * @param request contains attributes from json file to update a competition team
     * @return competition team
     *
     * Update current competition team after validation
     */
    fun update(competitionId: Long, competitionTeamId: Long, request: UpdateCompetitionTeamRequest): CompetitionTeam

    /**
     * @param competitionId is Id of the competition
     * @param competitionTeamId is Id of the competition team
     *
     * Delete competition team from competition after validation
     */
    fun delete(competitionId: Long, competitionTeamId: Long)

    /**
     * @param competitionId is Id of the competition
     * @param competitionTeamId is Id of the competition team
     * @param request contains attributes from json file to update a competition team captain
     * @return competition team
     *
     * Update status of current competition team captain
     */
    fun updateCaptain(competitionId: Long, competitionTeamId: Long, request: UpdateCompetitionTeamCaptainRequest): CompetitionTeam


}