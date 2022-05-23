package cz.cvut.fel.pivchart.api.controller

import cz.cvut.fel.pivchart.api.APIPath.PATH_V1
import cz.cvut.fel.pivchart.model.request.TeamRequest
import cz.cvut.fel.pivchart.model.request.UpdateCompetitionTeamCaptainRequest
import cz.cvut.fel.pivchart.model.request.UpdateCompetitionTeamRequest
import cz.cvut.fel.pivchart.model.response.CompetitionTeamResponse
import cz.cvut.fel.pivchart.service.facade.CompetitionTeamFacade
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("$PATH_V1/competitions/{competitionId}/teams")
class CompetitionTeamController (
        private val competitionTeamFacade:  CompetitionTeamFacade
) {
    @GetMapping
    fun getAll(@PathVariable competitionId: Long): ResponseEntity<MutableList<CompetitionTeamResponse>> {
        val competitionTeams = competitionTeamFacade.getAll(competitionId)
        return ResponseEntity(competitionTeams, HttpStatus.OK)
    }

    @PostMapping
    fun addCompetitionTeam(@PathVariable competitionId: Long, @RequestBody request: TeamRequest): ResponseEntity<CompetitionTeamResponse> {
        val competitionTeam = competitionTeamFacade.create(competitionId, request)
        return ResponseEntity(competitionTeam, HttpStatus.OK)
    }

    @PutMapping("/{competitionTeamId}")
    fun updateCompetitionTeam(@PathVariable competitionId: Long, @PathVariable competitionTeamId: Long, @RequestBody request: UpdateCompetitionTeamRequest): ResponseEntity<CompetitionTeamResponse>{
        val competitionTeam = competitionTeamFacade.update(competitionId,competitionTeamId,request)
        return ResponseEntity(competitionTeam ,HttpStatus.OK)
    }

    @DeleteMapping("/{competitionTeamId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCompetitionTeam(@PathVariable competitionId: Long, @PathVariable competitionTeamId: Long): ResponseEntity<Nothing> {
        competitionTeamFacade.delete(competitionId, competitionTeamId)
        return ResponseEntity(null, HttpStatus.NO_CONTENT)
    }

    @PostMapping("/{competitionTeamId}/captain")
    fun updateCaptain(@PathVariable competitionId: Long, @PathVariable competitionTeamId: Long, @RequestBody request: UpdateCompetitionTeamCaptainRequest): ResponseEntity<CompetitionTeamResponse> {
        val competitionTeam = competitionTeamFacade.updateCaptain(competitionId, competitionTeamId, request) //todo dont sure
        return ResponseEntity(competitionTeam ,HttpStatus.OK)
    }

}