package cz.cvut.fel.pivchart.model.response

/**
 * Gathering response in json format
 */
data class GatheringResponse(
    val id: Long?,
    val title: String,
    val pub: PubResponse,
    val isFinished: Boolean
)