package cz.cvut.fel.pivchart

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder

object UserContextSetter {

    fun setUser(email: String, roles: List<String>) {
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
            email, null, roles.map {
                SimpleGrantedAuthority("ROLE_${it.uppercase()}")
            }
        )
    }

    fun setUser(email: String) {
        setUser(email, listOf("USER"))
    }
}