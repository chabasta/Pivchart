package cz.cvut.fel.pivchart.repository

import cz.cvut.fel.pivchart.model.entity.User
import cz.cvut.fel.pivchart.model.request.IndexUsersRequest
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.criteria.Predicate

/**
 * Implementation of user repository
 */
@Repository
class UserRepositoryImpl(
    private val em: EntityManager
) : UserRepositoryFilterable<User, Long> {

    override fun filter(request: IndexUsersRequest): MutableList<User> {
        val cb = em.criteriaBuilder
        val cr = cb.createQuery(User::class.java)
        val root = cr.from(User::class.java)
        // Match everything if no criteria set
        val predicates = mutableListOf<Predicate>(cb.and())

        if (request.email != null)
            predicates.add(cb.like(root.get("email"), "%${request.email}%"))

        cr.select(root).where(cb.and(*predicates.toTypedArray()))
        return em.createQuery(cr).resultList
    }
}