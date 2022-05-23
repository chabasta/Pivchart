package cz.cvut.fel.pivchart.model.entity

import cz.cvut.fel.pivchart.model.entity.base.AbstractValidEntity
import javax.persistence.*

/**
 * Gathering entity
 */
@Entity
@Table(name = "gatherings")
open class Gathering : AbstractValidEntity<Long>() {

    @Column(name = "title", length = 128, nullable = false)
    open lateinit var title: String

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "pub_id")
    open lateinit var pub: Pub

    @Column(name = "is_finished")
    open var isFinished: Boolean = false

    @Column(name = "is_public")
    open var isPublic: Boolean = false

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "gathering")
    open var gatheringDrankDrinks: MutableList<GatheringDrankDrink> = ArrayList()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "gathering", cascade = [CascadeType.REMOVE])
    open var members: MutableList<GatheringMember> = ArrayList()
}