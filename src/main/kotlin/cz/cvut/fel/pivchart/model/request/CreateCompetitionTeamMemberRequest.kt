package cz.cvut.fel.pivchart.model.request

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Requires attributes in json format to create competition team member
 */
data class CreateCompetitionTeamMemberRequest(
    @JsonProperty("userId")
    val userId: Long
)