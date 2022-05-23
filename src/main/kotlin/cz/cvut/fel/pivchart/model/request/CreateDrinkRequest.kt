package cz.cvut.fel.pivchart.model.request

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Requires attributes in json format to create drink
 */
data class CreateDrinkRequest(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("alcoholVolume")
    val alcoholVolume: Double
)
