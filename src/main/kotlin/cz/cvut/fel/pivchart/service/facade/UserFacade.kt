package cz.cvut.fel.pivchart.service.facade

import cz.cvut.fel.pivchart.utils.Auth
import cz.cvut.fel.pivchart.utils.UserUtils
import cz.cvut.fel.pivchart.model.entity.User
import cz.cvut.fel.pivchart.model.request.IndexUsersRequest
import cz.cvut.fel.pivchart.model.request.UpdateUserRequest
import cz.cvut.fel.pivchart.model.response.UserResponse
import cz.cvut.fel.pivchart.service.interfaces.UserServiceI
import cz.cvut.fel.pivchart.service.validate.UserValidator
import org.springframework.stereotype.Service
import java.util.stream.Collectors

/**
 * User service facade
 */
@Service
class UserFacade(
    val userService: UserServiceI,
    val auth: Auth,
    val userValidator: UserValidator
) {
    fun getAll(request: IndexUsersRequest): List<UserResponse> {
        val users = userService.getAll(request)

        return users
            .stream()
            .map { prepareUserResponse(it) }
            .collect(Collectors.toList())
    }

    fun getMe(): UserResponse {
        val user = auth.get()

        return prepareUserResponse(user)
    }

    fun updateMe(request: UpdateUserRequest): UserResponse {
        userValidator.validateOnUpdate(request)
        val user = userService.update(request)

        return prepareUserResponse(user)
    }

    private fun prepareUserResponse(user: User): UserResponse {
        return UserUtils.prepareUserResponse(user)
    }
}