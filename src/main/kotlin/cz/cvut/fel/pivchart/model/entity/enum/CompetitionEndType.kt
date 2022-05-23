package cz.cvut.fel.pivchart.model.entity.enum

/**
 * Competition end types
 * LEADER_STOPS -> leader can stop the competition
 * TIME_ATTACK -> when the time is up
 * AMOUNT -> when the drink limit is reached
 */
enum class CompetitionEndType(val value: String) {
    LEADER_STOPS("LEADER_STOPS"),
    TIME_ATTACK("TIME_ATTACK"),
    AMOUNT("AMOUNT")
}