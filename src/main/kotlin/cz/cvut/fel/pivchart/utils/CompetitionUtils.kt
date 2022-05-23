package cz.cvut.fel.pivchart.utils

import cz.cvut.fel.pivchart.model.entity.Competition
import cz.cvut.fel.pivchart.model.response.CompetitionResponse

object CompetitionUtils {
    fun prepareCompetitionResponse(competition: Competition): CompetitionResponse {
        return CompetitionResponse(
            id = competition.id!!,
            title = competition.title,
            endType = competition.competitionEndType,
            startDateTime = competition.startDateTime,
            endDateTime = competition.endDateTime,
            isFinished = competition.isFinished,
            pub = PubUtils.preparePubResponse(competition.pub)
        )
    }
}