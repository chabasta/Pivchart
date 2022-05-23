package cz.cvut.fel.pivchart.utils

import cz.cvut.fel.pivchart.exceptions.PivchartException
import cz.cvut.fel.pivchart.model.entity.User
import cz.cvut.fel.pivchart.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class Auth (
    private val userRepository: UserRepository
){
    fun get() : User {
        val defaultOidcUser = SecurityContextHolder.getContext().authentication.takeIf {
            it.isAuthenticated
        }?.run {
            this.principal as String
        }

        return defaultOidcUser?.run {
            userRepository.findByEmail(this)
        }
            ?: throw PivchartException()
    }
}