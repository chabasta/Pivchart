package cz.cvut.fel.pivchart.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class IndexUsersRequest(
    @JsonProperty("email")
    val email: String?
)
