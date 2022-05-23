package cz.cvut.fel.pivchart.service.facade

import cz.cvut.fel.pivchart.utils.GatheringUtils
import cz.cvut.fel.pivchart.model.entity.Gathering
import cz.cvut.fel.pivchart.model.request.CreateGatheringRequest
import cz.cvut.fel.pivchart.model.request.UpdateGatheringRequest
import cz.cvut.fel.pivchart.model.response.GatheringResponse
import cz.cvut.fel.pivchart.service.interfaces.GatheringServiceI
import cz.cvut.fel.pivchart.service.validate.GatheringValidator
import org.springframework.stereotype.Service
import java.util.stream.Collectors

/**
 * Gathering service facade
 */
@Service
class GatheringFacade(
    private val gatheringService: GatheringServiceI,
    private val gatheringValidator: GatheringValidator
) {
    fun getAll(): MutableList<GatheringResponse> {
        val gatherings = gatheringService.getAll()

        return gatherings.stream()
            .map { prepareGatheringResponse(it) }
            .collect(Collectors.toList())
    }

    fun get(gatheringId: Long): GatheringResponse {
        gatheringValidator.validateOnGet(gatheringId)
        val gathering = gatheringService.get(gatheringId)

        return prepareGatheringResponse(gathering)
    }

    fun create(request: CreateGatheringRequest): GatheringResponse {
        gatheringValidator.validateOnCreate(request)
        val gathering = gatheringService.create(request)

        return prepareGatheringResponse(gathering)
    }

    fun update(gatheringId: Long, request: UpdateGatheringRequest): GatheringResponse {
        gatheringValidator.validateOnUpdate(gatheringId, request)
        val gathering = gatheringService.update(gatheringId, request)

        return prepareGatheringResponse(gathering)
    }

    fun delete(gatheringId: Long) {
        gatheringValidator.validateOnDelete(gatheringId)
        gatheringService.delete(gatheringId)
    }

    fun finish(gatheringId: Long): GatheringResponse {
        gatheringValidator.validateOnFinish(gatheringId)
        val gathering = gatheringService.finish(gatheringId)

        return prepareGatheringResponse(gathering)
    }

    private fun prepareGatheringResponse(gathering: Gathering): GatheringResponse {
        return GatheringUtils.prepareGatheringResponse(gathering)
    }
}