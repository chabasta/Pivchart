package cz.cvut.fel.pivchart.model.response

/**
 * User response in json format
 */
data class UserResponse(
    val id: Long,
    val email: String,
    val username: String,
    val sex: String?,
    val weight: String?
)