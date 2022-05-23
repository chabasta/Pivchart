package cz.cvut.fel.pivchart.api.controller

import cz.cvut.fel.pivchart.api.APIPath.PATH_V1
import cz.cvut.fel.pivchart.model.request.CreateCompetitionRequest
import cz.cvut.fel.pivchart.model.request.UpdateCompetitionRequest
import cz.cvut.fel.pivchart.model.response.CompetitionResponse
import cz.cvut.fel.pivchart.service.facade.CompetitionFacade
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("$PATH_V1/competitions")
class CompetitionController(
    private val competitionFacade: CompetitionFacade
) {
    @GetMapping
    fun getAll(): ResponseEntity<MutableList<CompetitionResponse>> {
        val competitions = competitionFacade.getAll()
        return ResponseEntity(competitions, HttpStatus.OK)
    }

    @GetMapping("/{competitionId}")
    fun getCompetition(@PathVariable competitionId: Long): ResponseEntity<CompetitionResponse> {
        val competition = competitionFacade.get(competitionId)
        return ResponseEntity(competition, HttpStatus.OK)
    }

    @PostMapping
    fun createCompetition(@RequestBody request: CreateCompetitionRequest): ResponseEntity<CompetitionResponse> {
        val competition = competitionFacade.create(request)
        return ResponseEntity(competition, HttpStatus.OK)
    }

    @PutMapping("/{competitionId}")
    fun updateCompetition(@PathVariable competitionId: Long, @RequestBody request: UpdateCompetitionRequest): ResponseEntity<CompetitionResponse> {
        val competition = competitionFacade.update(competitionId, request)
        return ResponseEntity(competition, HttpStatus.OK)
    }

    @PostMapping("/{competitionId}/finish")
    fun finishCompetition(@PathVariable competitionId: Long): ResponseEntity<CompetitionResponse> {
        val competition = competitionFacade.finish(competitionId)
        return ResponseEntity(competition, HttpStatus.OK)
    }

    @DeleteMapping("/{competitionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCompetition(@PathVariable competitionId: Long): ResponseEntity<Nothing> {
        competitionFacade.delete(competitionId)
        return ResponseEntity(null, HttpStatus.NO_CONTENT)
    }

}