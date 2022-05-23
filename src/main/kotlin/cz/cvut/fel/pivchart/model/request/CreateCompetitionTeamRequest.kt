package cz.cvut.fel.pivchart.model.request

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Requires attributes in json format to create competition team
 */
data class CreateCompetitionTeamRequest (
    @JsonProperty("teamId")
    val teamId: Long,
    @JsonProperty("competitionId")
    val competitionId: Long,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("pubId")
    val pubId: Long
)

