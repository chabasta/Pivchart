package cz.cvut.fel.pivchart.model.entity

import javax.persistence.*

/**
 * Competition drank drink entity
 */
@Entity
@DiscriminatorValue("competition")
open class CompetitionDrankDrink : DrankDrink() {

    @JoinColumn(name = "competition_id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    open lateinit var competition: Competition

}