package cz.cvut.fel.pivchart.model.request

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Requires attributes in json format to update drink
 */
data class UpdateDrinkRequest(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("alcoholVolume")
    val alcoholVolume: Double
)