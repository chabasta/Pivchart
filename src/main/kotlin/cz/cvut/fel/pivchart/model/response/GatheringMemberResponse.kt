package cz.cvut.fel.pivchart.model.response

/**
 * Gathering member response in json format
 */
data class GatheringMemberResponse(
    val gathering: GatheringResponse,
    val user: UserResponse,
    val isOwner: Boolean
)
