package cz.cvut.fel.pivchart.model.entity

import cz.cvut.fel.pivchart.model.entity.base.AbstractValidEntity
import javax.persistence.*

/**
 * Drink entity
 */
@Entity
@Table(name = "drinks")
open class Drink : AbstractValidEntity<Long>() {

    @Column(name = "name", length = 128, nullable = false)
    open lateinit var name: String

    @Column(name = "alcohol_volume", nullable = false)
    open var alcoholVolume: Double? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "drink")
    open val pubDrink: List<PubDrink> = ArrayList()
}