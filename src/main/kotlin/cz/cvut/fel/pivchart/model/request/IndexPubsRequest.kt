package cz.cvut.fel.pivchart.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class IndexPubsRequest(
    @JsonProperty("name")
    val name: String?,
    @JsonProperty("latitude")
    val latitude: Double?,
    @JsonProperty("longitude")
    val longitude: Double?,
    @JsonProperty("radius")
    val radius: Double = 500.0
)
