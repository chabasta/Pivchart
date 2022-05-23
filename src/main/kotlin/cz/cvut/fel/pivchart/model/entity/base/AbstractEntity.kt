package cz.cvut.fel.pivchart.model.entity.base

import java.io.Serializable
import javax.persistence.*

/**
 * Abstract entity class that contains basic values of entity,
 * like ID, method equals, method hashCode
 */
@MappedSuperclass
abstract class AbstractEntity<U : Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    open val id: U? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AbstractEntity<*>

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}