package cz.cvut.fel.pivchart.model.request

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Requires attributes in json format to update gathering
 */
data class UpdateGatheringRequest(
    @JsonProperty("title")
    val title: String,
    @JsonProperty("pubId")
    val pubId: Long,
    @JsonProperty("memberIds")
    val memberIds: Set<Long>
)
