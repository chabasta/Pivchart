package cz.cvut.fel.pivchart.api.controller

import cz.cvut.fel.pivchart.api.APIPath.PATH_V1
import cz.cvut.fel.pivchart.model.request.CreatePubDrinkRequest
import cz.cvut.fel.pivchart.model.request.UpdatePubDrinkRequest
import cz.cvut.fel.pivchart.model.response.PubDrinkResponse
import cz.cvut.fel.pivchart.service.facade.PubDrinkFacade
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("$PATH_V1/pubs/{pubId}/drinks")
class PubDrinkController(
    private val pubDrinkFacade: PubDrinkFacade
) {
    @GetMapping
    fun getAll(@PathVariable pubId: Long): ResponseEntity<MutableList<PubDrinkResponse>> {
        val pubDrinks = pubDrinkFacade.getAll(pubId)
        return ResponseEntity(pubDrinks, HttpStatus.OK)
    }

    @PostMapping
    fun createPubDrink(@PathVariable pubId: Long, @RequestBody request: CreatePubDrinkRequest): ResponseEntity<PubDrinkResponse> {
        val pubDrink = pubDrinkFacade.create(pubId, request)
        return ResponseEntity(pubDrink, HttpStatus.OK)
    }

    @GetMapping("/{pubDrinkId}")
    fun getPubDrink(@PathVariable pubId: Long, @PathVariable pubDrinkId: Long): ResponseEntity<PubDrinkResponse> {
        val pubDrink = pubDrinkFacade.get(pubId, pubDrinkId)
        return ResponseEntity(pubDrink, HttpStatus.OK)
    }

    @PutMapping("/{pubDrinkId}")
    fun updatePubDrink(@PathVariable pubId: Long, @PathVariable pubDrinkId: Long, @RequestBody request: UpdatePubDrinkRequest): ResponseEntity<PubDrinkResponse> {
        val pubDrink = pubDrinkFacade.update(pubId, pubDrinkId, request)
        return ResponseEntity(pubDrink, HttpStatus.OK)
    }

    @DeleteMapping("/{pubDrinkId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePubDrink(@PathVariable pubId: Long, @PathVariable pubDrinkId: Long) {
        pubDrinkFacade.delete(pubId, pubDrinkId)
    }
}