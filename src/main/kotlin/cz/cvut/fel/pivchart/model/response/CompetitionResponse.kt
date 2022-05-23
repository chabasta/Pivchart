package cz.cvut.fel.pivchart.model.response

import cz.cvut.fel.pivchart.model.entity.enum.CompetitionEndType
import java.time.LocalDateTime

/**
 * Competition response in json format
 */
data class CompetitionResponse(
    val id: Long,
    val title: String,
    val startDateTime: LocalDateTime?,
    val endDateTime: LocalDateTime?,
    val endType: CompetitionEndType,
    val isFinished: Boolean,
    val pub: PubResponse
)