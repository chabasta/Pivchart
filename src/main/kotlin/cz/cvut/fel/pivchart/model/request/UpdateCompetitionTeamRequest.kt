package cz.cvut.fel.pivchart.model.request

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Requires attributes in json format to update competition team
 */
data class UpdateCompetitionTeamRequest(
    @JsonProperty("title")
    val title: String,
    @JsonProperty("membersIds")
    val membersIds: Set<Long>
)