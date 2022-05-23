package cz.cvut.fel.pivchart.model.entity

import cz.cvut.fel.pivchart.model.entity.base.AbstractValidEntity
import javax.persistence.*

/**
 * Pub drink entity
 */
@Entity
@Table(name = "pub_drinks")
open class PubDrink : AbstractValidEntity<Long>() {

    @Column(name = "price", nullable = false)
    open var price: Double? = null

    @Column(name = "volume", nullable = false)
    open var volume: Int? = null

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "drink_id")
    open lateinit var drink: Drink

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "pub_id")
    open lateinit var pub: Pub

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pubDrink")
    open var drankDrinks: MutableList<DrankDrink> = ArrayList()
}