package cz.cvut.fel.pivchart.repository

import cz.cvut.fel.pivchart.model.entity.User
import cz.cvut.fel.pivchart.model.request.IndexUsersRequest
import org.springframework.stereotype.Repository

/**
 * User repository filter
 */
@Repository
interface UserRepositoryFilterable<T, ID> {
    fun filter(request: IndexUsersRequest): MutableList<User>
}