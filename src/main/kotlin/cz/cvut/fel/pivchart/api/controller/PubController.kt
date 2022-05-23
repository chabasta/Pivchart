package cz.cvut.fel.pivchart.api.controller

import cz.cvut.fel.pivchart.api.APIPath.PATH_V1
import cz.cvut.fel.pivchart.model.request.CreatePubRequest
import cz.cvut.fel.pivchart.model.request.IndexPubsRequest
import cz.cvut.fel.pivchart.model.request.UpdatePubRequest
import cz.cvut.fel.pivchart.model.response.PubResponse
import cz.cvut.fel.pivchart.service.facade.PubFacade
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("$PATH_V1/pubs")
class PubController(
    private val pubFacade: PubFacade
) {
    @GetMapping
    fun getAll(request: IndexPubsRequest): ResponseEntity<List<PubResponse>> {
        val pubResponses = pubFacade.getAll(request)
        return ResponseEntity(pubResponses, HttpStatus.OK)
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    fun createPub(@RequestBody request: CreatePubRequest): ResponseEntity<PubResponse> {
        val pubResponse = pubFacade.create(request)
        return ResponseEntity(pubResponse, HttpStatus.OK)
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    fun updatePub(@PathVariable id: Long, @RequestBody request: UpdatePubRequest): ResponseEntity<PubResponse> {
        val pubResponse = pubFacade.update(id, request)
        return ResponseEntity(pubResponse, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getPub(@PathVariable id: Long): ResponseEntity<PubResponse> {
        val pubResponse = pubFacade.get(id)
        return ResponseEntity(pubResponse, HttpStatus.OK)
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePub(@PathVariable id: Long) {
        pubFacade.delete(id)
    }
}