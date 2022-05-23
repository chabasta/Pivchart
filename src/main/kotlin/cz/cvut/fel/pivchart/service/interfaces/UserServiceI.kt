package cz.cvut.fel.pivchart.service.interfaces

import cz.cvut.fel.pivchart.model.entity.User
import cz.cvut.fel.pivchart.model.request.IndexUsersRequest
import cz.cvut.fel.pivchart.model.request.UpdateUserRequest

interface UserServiceI {

    /**
     * @param request contains attributes from json file
     * @return list of users
     *
     * Return all users with the same email in request
     */
    fun getAll(request: IndexUsersRequest) : List<User>

    /**
     * @param userId is Id of required user
     * @return user
     *
     * Get user by Id
     */
    fun get(userId: Long): User

    /**
     * @param request contains attributes from json file to update user
     * @return user
     *
     * Update user and return him
     */
    fun update(request: UpdateUserRequest): User


}