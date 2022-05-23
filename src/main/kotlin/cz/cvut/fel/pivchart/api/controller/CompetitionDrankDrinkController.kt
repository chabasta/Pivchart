package cz.cvut.fel.pivchart.api.controller

import cz.cvut.fel.pivchart.api.APIPath.PATH_V1
import cz.cvut.fel.pivchart.model.request.CreateCompetitionDrinkRequest
import cz.cvut.fel.pivchart.model.response.CompetitionDrankDrinkResponse
import cz.cvut.fel.pivchart.service.facade.CompetitionDrankDrinkFacade
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("$PATH_V1/competitions/{competitionId}/drinks")
class CompetitionDrankDrinkController(
    private val competitionDrankDrinkFacade: CompetitionDrankDrinkFacade
) {

    @GetMapping
    fun getAll(@PathVariable competitionId: Long): ResponseEntity<MutableList<CompetitionDrankDrinkResponse>> {
        val competitionDrankDrinks = competitionDrankDrinkFacade.getAll(competitionId)
        return ResponseEntity(competitionDrankDrinks, HttpStatus.OK)
    }

    @PostMapping
    fun addCompetitionDrink(@PathVariable competitionId: Long, @RequestBody request: CreateCompetitionDrinkRequest): ResponseEntity<CompetitionDrankDrinkResponse> {
        val competitionDrankDrink = competitionDrankDrinkFacade.create(competitionId, request)
        return ResponseEntity(competitionDrankDrink, HttpStatus.OK)
    }

   @DeleteMapping("/{competitionDrinkId}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   fun deleteCompetitionDrankDrink(@PathVariable competitionId: Long, @PathVariable competitionDrinkId: Long): ResponseEntity<Nothing> {
        competitionDrankDrinkFacade.delete(competitionId, competitionDrinkId)
       return ResponseEntity(null, HttpStatus.NO_CONTENT)
   }
}