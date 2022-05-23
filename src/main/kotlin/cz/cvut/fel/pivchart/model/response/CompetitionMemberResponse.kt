package cz.cvut.fel.pivchart.model.response

/**
 * Competition member response in json format
 */
data class CompetitionMemberResponse(
    val isCaptain: Boolean,
    val user: UserResponse
)