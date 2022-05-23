package cz.cvut.fel.pivchart.model.request

import com.fasterxml.jackson.annotation.JsonProperty
import cz.cvut.fel.pivchart.model.entity.enum.Sex

/**
 * Requires attributes in json format to update user
 */
data class UpdateUserRequest(
    @JsonProperty("email")
    val email: String,
    @JsonProperty("username")
    val username: String,
    @JsonProperty("sex")
    val sex: Sex,
    @JsonProperty("weight")
    val weight: Double
)