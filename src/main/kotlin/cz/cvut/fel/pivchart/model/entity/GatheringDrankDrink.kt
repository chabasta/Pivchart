package cz.cvut.fel.pivchart.model.entity

import javax.persistence.*

/**
 * Gathering entity
 */
@Entity
@DiscriminatorValue("gathering")
open class GatheringDrankDrink : DrankDrink() {

    @JoinColumn(name = "gathering_id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    open lateinit var gathering: Gathering

}