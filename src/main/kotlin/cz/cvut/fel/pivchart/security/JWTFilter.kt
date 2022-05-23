package cz.cvut.fel.pivchart.security

import com.auth0.jwt.exceptions.JWTVerificationException
import com.fasterxml.jackson.databind.ObjectMapper
import cz.cvut.fel.pivchart.model.entity.User
import cz.cvut.fel.pivchart.model.response.ErrorResponse
import cz.cvut.fel.pivchart.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JWTFilter(
    private val jwtUtil: JwtUtil,
    private val userRepository: UserRepository,
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authHeader = request.getHeader("Authorization")
        if (authHeader != null && authHeader.isNotBlank() && authHeader.startsWith("Bearer ")) {
            val jwt = authHeader.substring(7)
            try {
                SecurityContextHolder.getContext().authentication = processAuthToken(jwt)
            } catch (e: JWTVerificationException) {
                val errorBody = ErrorResponse(
                    message = e.message,
                )
                val json = objectMapper.writeValueAsString(errorBody)

                logger.info("Users token expired")
                response.apply {
                    this.contentType = "application/json;charset=UTF-8"
                    this.status = HttpStatus.FORBIDDEN.value()
                    this.writer.write(json)
                }
                return
            }
        }

        logger.info("User does not have an Authorization header")
        filterChain.doFilter(request, response)
    }

    private fun processAuthToken(jwt: String) : Authentication {
        val jwtDecoded = jwtUtil.validateToken(jwt)
        val email = jwtDecoded.subject
        logger.info("Precessing users jwt token $email")
        val authorities = jwtDecoded.getClaim("roles")?.asArray(String::class.java)?.map {
            SimpleGrantedAuthority(it)
        }

        createIfNotExists(email)

        return UsernamePasswordAuthenticationToken(email, null, authorities)
    }

    private fun createIfNotExists(email: String) {
        if (!userRepository.existsUserByEmail(email)) {
            logger.info("Creating new user $email")
            userRepository.save(
                User().apply {
                    this.email = email
                    this.username = email.substringBefore('@')
                }
            )
        }
    }
}