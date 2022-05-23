package cz.cvut.fel.pivchart.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtUtil {
    companion object {
        private const val APP_NAME = "pivchart"
    }

    @Value("\${jwt.secret}")
    private val secret: String? = null

    private val algorithm
        get() = Algorithm.HMAC256(secret)
    private val verifier
        get() = JWT.require(algorithm).withIssuer(APP_NAME).build()

    fun validateToken(accessToken: String): DecodedJWT {
        return verifier.verify(accessToken)
    }
}