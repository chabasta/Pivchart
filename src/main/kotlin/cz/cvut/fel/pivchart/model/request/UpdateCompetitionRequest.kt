package cz.cvut.fel.pivchart.model.request

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Requires attributes in json format to update competition
 */
data class UpdateCompetitionRequest (
    @JsonProperty("title")
    val title: String
)