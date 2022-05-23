package cz.cvut.fel.pivchart.model.request

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Requires attributes in json format of the team
 */
data class TeamRequest (
    @JsonProperty("title")
    val title: String,
    @JsonProperty("captainId")
    val captainId: Long
)