package cz.cvut.fel.pivchart.api.controller

import cz.cvut.fel.pivchart.api.APIPath.PATH_V1
import cz.cvut.fel.pivchart.model.request.CreateDrinkRequest
import cz.cvut.fel.pivchart.model.request.UpdateDrinkRequest
import cz.cvut.fel.pivchart.model.response.DrinkResponse
import cz.cvut.fel.pivchart.service.facade.DrinkFacade
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("$PATH_V1/drinks")
class DrinkController(
    private val drinkFacade: DrinkFacade
) {
    @GetMapping
    fun getAll(): ResponseEntity<List<DrinkResponse>> {
        val drinkResponses = drinkFacade.getAll()
        return ResponseEntity(drinkResponses, HttpStatus.OK)
    }

    @PostMapping
    fun createDrink(@RequestBody request: CreateDrinkRequest): ResponseEntity<DrinkResponse> {
        val drinkResponse = drinkFacade.create(request)
        return ResponseEntity(drinkResponse, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun updateDrink(@PathVariable id: Long, @RequestBody request: UpdateDrinkRequest): ResponseEntity<DrinkResponse> {
        val drinkResponse = drinkFacade.update(id, request)
        return ResponseEntity(drinkResponse, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getDrink(@PathVariable id: Long): ResponseEntity<DrinkResponse> {
        val drinkResponse = drinkFacade.get(id)
        return ResponseEntity(drinkResponse, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteDrink(@PathVariable id: Long) {
        drinkFacade.delete(id)
    }
}