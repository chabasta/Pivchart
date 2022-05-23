package cz.cvut.fel.pivchart.model.request

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Requires attributes in json format to update status of competition team captain
 */
data class UpdateCompetitionTeamCaptainRequest(
    @JsonProperty("captainId")
    val captainId: Long
)
