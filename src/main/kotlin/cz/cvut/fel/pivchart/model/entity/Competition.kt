package cz.cvut.fel.pivchart.model.entity

import cz.cvut.fel.pivchart.model.entity.base.AbstractValidEntity
import cz.cvut.fel.pivchart.model.entity.enum.CompetitionEndType
import java.time.LocalDateTime
import javax.persistence.*

/**
 * Competition entity
 */
@Entity
@Table(name = "competitions")
open class Competition : AbstractValidEntity<Long>() {

    @Column(name = "title", length = 128, nullable = false)
    open lateinit var title: String

    @Column(name = "description", length = 300)
    open lateinit var description: String

    @Column(name = "amount")
    open var amount: Int? = null

    @Column(name = "start_date_time")
    open var startDateTime: LocalDateTime? = null

    @Column(name = "end_date_time")
    open var endDateTime: LocalDateTime? = null

    @Column(name = "is_finished")
    open var isFinished: Boolean = false

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "pub_id")
    open lateinit var pub: Pub

    @OneToOne( fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id")
    open lateinit var owner: User

    @Enumerated(EnumType.STRING)
    @Column(name = "end_type", nullable = false)
    open lateinit var competitionEndType: CompetitionEndType

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "competition")
    open var competitionDrankDrinks: MutableList<CompetitionDrankDrink> = ArrayList()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "competition")
    open var competitionTeams: MutableList<CompetitionTeam> = ArrayList()
}