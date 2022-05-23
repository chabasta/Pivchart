package cz.cvut.fel.pivchart.api.controller

import cz.cvut.fel.pivchart.api.APIPath.PATH_V1
import cz.cvut.fel.pivchart.model.request.CreateGatheringMemberRequest
import cz.cvut.fel.pivchart.model.response.GatheringMemberResponse
import cz.cvut.fel.pivchart.service.facade.GatheringMemberFacade
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("$PATH_V1/gatherings/{gatheringId}/members")
class GatheringMemberController (
    private val gatheringMemberFacade: GatheringMemberFacade
) {
    @GetMapping
    fun getAll(@PathVariable gatheringId: Long): ResponseEntity<List<GatheringMemberResponse>> {
        val members = gatheringMemberFacade.getAll(gatheringId)
        return ResponseEntity<List<GatheringMemberResponse>>(members, HttpStatus.OK)
    }

    @PostMapping
    fun createGatheringMember(@PathVariable gatheringId: Long, @RequestBody request: CreateGatheringMemberRequest): ResponseEntity<GatheringMemberResponse> {
        val member = gatheringMemberFacade.create(gatheringId, request)
        return ResponseEntity<GatheringMemberResponse>(member, HttpStatus.OK)
    }

    @DeleteMapping("/{gatheringMemberId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteGatheringMember(@PathVariable gatheringId: Long, @PathVariable gatheringMemberId: Long) {
        gatheringMemberFacade.delete(gatheringId, gatheringMemberId)
    }
}