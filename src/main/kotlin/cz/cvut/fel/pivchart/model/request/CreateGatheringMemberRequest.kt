package cz.cvut.fel.pivchart.model.request

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Requires attributes in json format to create gathering member
 */
data class CreateGatheringMemberRequest(
    @JsonProperty("userId")
    val userId: Long
)
