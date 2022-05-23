package cz.cvut.fel.pivchart.repository

import cz.cvut.fel.pivchart.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * User repository
 */
@Repository
interface UserRepository : JpaRepository<User, Long>, UserRepositoryFilterable<User, Long> {
    fun findByEmail(email: String): User?

    fun existsUserByEmail(email: String): Boolean
}