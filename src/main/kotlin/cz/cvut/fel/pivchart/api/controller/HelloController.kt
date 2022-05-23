package cz.cvut.fel.pivchart.api.controller

import cz.cvut.fel.pivchart.api.APIPath.PATH_V1
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(PATH_V1)
class HelloController {

    @GetMapping("/user")
    fun getUser(): ResponseEntity<Any> {
        val authentication = SecurityContextHolder.getContext().authentication
        return ResponseEntity(authentication, HttpStatus.OK)
    }

    //    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // thanks to "securedEnabled = true" is @Secured enough
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin")
    fun getAdmin(): ResponseEntity<Map<String, String>> {
        return ResponseEntity(mapOf("Hello" to "admin"), HttpStatus.OK)
    }
}