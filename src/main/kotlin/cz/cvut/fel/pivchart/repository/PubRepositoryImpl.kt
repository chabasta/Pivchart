package cz.cvut.fel.pivchart.repository

import cz.cvut.fel.pivchart.model.entity.Pub
import cz.cvut.fel.pivchart.model.request.IndexPubsRequest
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.criteria.Expression
import javax.persistence.criteria.Predicate

/**
 * Implementation of pub repository
 */
@Repository
class PubRepositoryImpl(
    private val em: EntityManager
) : PubRepositoryFilterable<Pub, Long> {

    override fun filter(request: IndexPubsRequest): MutableList<Pub> {
        val cb = em.criteriaBuilder
        val cr = cb.createQuery(Pub::class.java)
        val root = cr.from(Pub::class.java)
        // Match everything if no criteria set
        val predicates = mutableListOf<Predicate>(cb.and())

        if (request.name != null) {
            predicates.add(cb.like(root.get("name"), "%${request.name}%"))
        }

        if (request.latitude != null && request.longitude != null) {
            val point1: Expression<Pub> = cb.function(
                "point", Pub::class.java,
                root.get<Double>("latitude"), root.get<Double>("longitude")
            )
            val point2: Expression<Double> = cb.function(
                "point", Double::class.java,
                cb.literal(request.latitude), cb.literal(request.longitude)
            )
            val distance: Expression<Number> = cb.function(
                "ST_Distance_Sphere", Number::class.java,
                point1, point2
            )
            predicates.add(cb.lt(distance, request.radius))
        }


        cr.select(root).where(cb.and(*predicates.toTypedArray()))
        return em.createQuery(cr).resultList
    }
}