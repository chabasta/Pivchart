package cz.cvut.fel.pivchart.utils

import cz.cvut.fel.pivchart.model.entity.Gathering
import cz.cvut.fel.pivchart.model.response.GatheringResponse

object GatheringUtils {
    fun prepareGatheringResponse(gathering: Gathering): GatheringResponse {
        return GatheringResponse(
                id = gathering.id,
                title = gathering.title,
                pub = PubUtils.preparePubResponse(gathering.pub),
                isFinished = gathering.isFinished
        )
    }
}