package cz.cvut.fel.pivchart.service.facade

import cz.cvut.fel.pivchart.utils.PubDrinkUtils
import cz.cvut.fel.pivchart.utils.UserUtils
import cz.cvut.fel.pivchart.model.entity.GatheringDrankDrink
import cz.cvut.fel.pivchart.model.request.CreateGatheringDrinkRequest
import cz.cvut.fel.pivchart.model.response.GatheringDrankDrinkResponse
import cz.cvut.fel.pivchart.service.interfaces.GatheringDrankDrinkServiceI
import cz.cvut.fel.pivchart.service.validate.GatheringDrinkValidator
import org.springframework.stereotype.Service
import java.util.stream.Collectors

/**
 * Gathering drink service facade
 */
@Service
class GatheringDrankDrinkFacade (
    private val gatheringDrankDrinkService: GatheringDrankDrinkServiceI,
    private val gatheringDrinkValidator: GatheringDrinkValidator,
) {
    fun getAll(gatheringId: Long): MutableList<GatheringDrankDrinkResponse> {
        gatheringDrinkValidator.validateOnGetAll(gatheringId)
        val gatheringDrankDrinks = gatheringDrankDrinkService.getAll(gatheringId)

        return gatheringDrankDrinks
            .stream()
            .map { prepareResponse(it) }
            .collect(Collectors.toList())
    }

    fun create(gatheringId: Long, request: CreateGatheringDrinkRequest): GatheringDrankDrinkResponse {
        gatheringDrinkValidator.validateOnCreate(gatheringId, request)
        val gatheringDrankDrink = gatheringDrankDrinkService.create(gatheringId, request)

        return prepareResponse(gatheringDrankDrink)
    }

    fun delete(gatheringId: Long, gatheringDrinkId: Long) {
        gatheringDrinkValidator.validateOnDelete(gatheringId, gatheringDrinkId)
        gatheringDrankDrinkService.delete(gatheringId, gatheringDrinkId)
    }

    private fun prepareResponse(gatheringDrink: GatheringDrankDrink): GatheringDrankDrinkResponse {
        return GatheringDrankDrinkResponse(
            id = gatheringDrink.id!!,
            pubDrink = PubDrinkUtils.preparePubDrinkResponse(gatheringDrink.pubDrink),
            createdAt = gatheringDrink.createdAt,
            drankBy = UserUtils.prepareUserResponse(gatheringDrink.user)
        )
    }
}