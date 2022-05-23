package cz.cvut.fel.pivchart.model.entity.base

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass

/**
 * Abstract entity class that contains basic crud values of entity,
 * like time of creation, method time of update, method time of delete
 */
@MappedSuperclass
abstract class AbstractValidEntity<U : Serializable>(

    @Column(name = "created_at", nullable = false)
    open val createdAt: LocalDateTime = LocalDateTime.now()

) : AbstractEntity<U>() {

    @Column(name = "updated_at")
    open var updatedAt: LocalDateTime? = null

    @Column(name = "deleted_at")
    open var deletedAt: LocalDateTime? = null
}