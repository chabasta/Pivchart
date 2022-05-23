package cz.cvut.fel.pivchart.service

import cz.cvut.fel.pivchart.utils.Auth
import cz.cvut.fel.pivchart.model.entity.User
import cz.cvut.fel.pivchart.model.request.IndexUsersRequest
import cz.cvut.fel.pivchart.model.request.UpdateUserRequest
import cz.cvut.fel.pivchart.repository.UserRepository
import cz.cvut.fel.pivchart.service.interfaces.UserServiceI
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * User service
 */
@Service
class UserService(
    val userRepository: UserRepository,
    val auth: Auth
) : UserServiceI {
    @Transactional
    override fun getAll(request: IndexUsersRequest): List<User> {
        return userRepository.filter(request)
    }

    @Transactional
    override fun get(userId: Long): User {
        return userRepository.getById(userId)
    }

    @Transactional
    override fun update(request: UpdateUserRequest): User {
        val user = auth.get()
        user.apply {
            username = request.username
            sex = request.sex
            weight = request.weight
        }

        return userRepository.save(user)
    }
}