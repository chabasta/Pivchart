package cz.cvut.fel.pivchart.model.entity

import cz.cvut.fel.pivchart.model.entity.base.AbstractValidEntity
import javax.persistence.*

/**
 * Competition team entity
 */
@Entity
@Table(name = "competition_teams")
open class CompetitionTeam : AbstractValidEntity<Long>() {

    @Column(name = "title", length = 128, nullable = false)
    open lateinit var title: String

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "competition_id")
    open lateinit var competition: Competition

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "competitionTeam")
    open var competitionTeamMembers: MutableList<CompetitionTeamMember> = ArrayList()
}