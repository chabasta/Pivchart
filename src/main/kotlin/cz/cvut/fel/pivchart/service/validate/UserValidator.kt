package cz.cvut.fel.pivchart.service.validate

import cz.cvut.fel.pivchart.utils.Auth
import cz.cvut.fel.pivchart.exceptions.api.NotFoundException
import cz.cvut.fel.pivchart.exceptions.api.ForbiddenException
import cz.cvut.fel.pivchart.model.entity.User
import cz.cvut.fel.pivchart.model.request.UpdateUserRequest
import cz.cvut.fel.pivchart.repository.UserRepository
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.stereotype.Component

/**
 * User validation
 */
@Component
class UserValidator(
    private val userRepository: UserRepository,
    private val auth: Auth

) {
    fun validateOnGet(userId: Long) {
        validateUserExists(userId)
    }

    fun validateOnUpdate(request: UpdateUserRequest) {
        val authUser = auth.get()

        if (request.email != authUser.email) throw ForbiddenException("Unauthorized to edit info of other users")
    }

    /**
     * @param userId is Id of the user
     *
     * Validate if user exist. If it doesn't exist -> Exception
     */
    @Throws(NotFoundException::class)
    fun validateUserExists(userId: Long): User {
        try {
            return userRepository.getById(userId)
        } catch (ex: JpaObjectRetrievalFailureException) {
            throw NotFoundException("User doesnt exit.")
        }
    }
}