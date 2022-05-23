package cz.cvut.fel.pivchart.model.request

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Requires attributes in json format to create pub
 */
data class CreatePubRequest(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("city")
    val city: String,
    @JsonProperty("street")
    val street: String,
    @JsonProperty("country")
    val country: String,
    @JsonProperty("postalCode")
    val postalCode: String,
    @JsonProperty("latitude")
    val latitude: Double?,
    @JsonProperty("longitude")
    val longitude: Double?,
    @JsonProperty("pubOwner")
    val pubOwner: Long
)
