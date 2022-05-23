package cz.cvut.fel.pivchart.api.controller

import cz.cvut.fel.pivchart.api.APIPath.PATH_V1
import cz.cvut.fel.pivchart.model.request.IndexUsersRequest
import cz.cvut.fel.pivchart.model.request.UpdateUserRequest
import cz.cvut.fel.pivchart.model.response.UserResponse
import cz.cvut.fel.pivchart.service.facade.UserFacade
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("$PATH_V1/users")
class UserController(
    private val userFacade: UserFacade
) {
    @GetMapping
    fun getAll(request: IndexUsersRequest): ResponseEntity<List<UserResponse>> {
        return ResponseEntity(userFacade.getAll(request), HttpStatus.OK)
    }

    @GetMapping("/me")
    fun getMe(): ResponseEntity<UserResponse> {
        val user = userFacade.getMe()
        return ResponseEntity(user, HttpStatus.OK)
    }

    @PutMapping("/me")
    fun updateMe(@RequestBody request: UpdateUserRequest): UserResponse {
        return userFacade.updateMe(request)
    }
}