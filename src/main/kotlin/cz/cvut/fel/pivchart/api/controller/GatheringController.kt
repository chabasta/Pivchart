package cz.cvut.fel.pivchart.api.controller

import cz.cvut.fel.pivchart.api.APIPath.PATH_V1
import cz.cvut.fel.pivchart.model.request.CreateGatheringRequest
import cz.cvut.fel.pivchart.model.request.UpdateGatheringRequest
import cz.cvut.fel.pivchart.model.response.GatheringResponse
import cz.cvut.fel.pivchart.service.facade.GatheringFacade
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("$PATH_V1/gatherings")
class GatheringController(
    private val gatheringFacade: GatheringFacade
) {
    @GetMapping
    fun getAll(): ResponseEntity<List<GatheringResponse>> {
        val gatherings = gatheringFacade.getAll()
        return ResponseEntity(gatherings, HttpStatus.OK)
    }

    @PostMapping
    fun createGathering(@RequestBody request: CreateGatheringRequest): ResponseEntity<GatheringResponse> {
        val gathering = gatheringFacade.create(request)
        return ResponseEntity(gathering, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun updateGathering(@PathVariable id: Long, @RequestBody request: UpdateGatheringRequest): ResponseEntity<GatheringResponse> {
        val gathering = gatheringFacade.update(id, request)
        return ResponseEntity(gathering, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getGathering(@PathVariable id: Long): ResponseEntity<GatheringResponse> {
        val gathering = gatheringFacade.get(id)
        return ResponseEntity(gathering, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteGathering(@PathVariable id: Long) {
        gatheringFacade.delete(id)
    }

    @PostMapping("/{id}/finish")
    fun finishGathering(@PathVariable id: Long): ResponseEntity<Nothing> {
        gatheringFacade.finish(id)
        return ResponseEntity(null, HttpStatus.NO_CONTENT)
    }
}