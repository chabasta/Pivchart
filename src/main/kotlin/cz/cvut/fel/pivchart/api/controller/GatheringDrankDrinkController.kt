package cz.cvut.fel.pivchart.api.controller

import cz.cvut.fel.pivchart.api.APIPath.PATH_V1
import cz.cvut.fel.pivchart.model.request.CreateGatheringDrinkRequest
import cz.cvut.fel.pivchart.model.response.GatheringDrankDrinkResponse
import cz.cvut.fel.pivchart.service.facade.GatheringDrankDrinkFacade
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("$PATH_V1/gatherings/{gatheringId}/drinks")
class GatheringDrankDrinkController(
    private val gatheringDrankDrinkFacade: GatheringDrankDrinkFacade
) {
    @GetMapping
    fun getAll(@PathVariable gatheringId: Long): ResponseEntity<MutableList<GatheringDrankDrinkResponse>> {
        val gatheringDrinks = gatheringDrankDrinkFacade.getAll(gatheringId)
        return ResponseEntity(gatheringDrinks, HttpStatus.OK)
    }

    @PostMapping
    fun addGatheringDrink(@PathVariable gatheringId: Long, @RequestBody request: CreateGatheringDrinkRequest): ResponseEntity<GatheringDrankDrinkResponse> {
        val gatheringDrink = gatheringDrankDrinkFacade.create(gatheringId, request)
        return ResponseEntity(gatheringDrink, HttpStatus.OK)
    }

    @DeleteMapping("/{gatheringDrinkId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteGatheringDrink(@PathVariable gatheringId: Long, @PathVariable gatheringDrinkId: Long) {
        gatheringDrankDrinkFacade.delete(gatheringId, gatheringDrinkId)
    }
}