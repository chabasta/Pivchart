package cz.cvut.fel.pivchart.utils

import cz.cvut.fel.pivchart.model.entity.User
import cz.cvut.fel.pivchart.model.response.UserResponse

object UserUtils {
    fun prepareUserResponse(user: User): UserResponse {
        return UserResponse(
            id = user.id!!,
            email = user.email,
            username = user.username,
            sex = user.sex?.toString(),
            weight = user.weight?.toString()
        )
    }
}