package cz.cvut.fel.pivchart.service

import cz.cvut.fel.pivchart.utils.Auth
import cz.cvut.fel.pivchart.exceptions.api.BadRequestException
import cz.cvut.fel.pivchart.model.entity.CompetitionDrankDrink
import cz.cvut.fel.pivchart.model.request.CreateCompetitionDrinkRequest
import cz.cvut.fel.pivchart.repository.CompetitionDrinkRepository
import cz.cvut.fel.pivchart.repository.CompetitionRepository
import cz.cvut.fel.pivchart.repository.PubDrinkRepository
import cz.cvut.fel.pivchart.service.interfaces.CompetitionDrankDrinkServiceI
import org.springframework.stereotype.Service
import javax.transaction.Transactional

/**
 * Competition drank drink service
 */
@Service
class CompetitionDrankDrinkService(
    private val competitionRepository: CompetitionRepository,
    private val pubDrinkRepository: PubDrinkRepository,
    private val competitionDrinkRepository: CompetitionDrinkRepository,
    private val auth: Auth
): CompetitionDrankDrinkServiceI {

    @Transactional
    override fun getAll(competitionId: Long): MutableList<CompetitionDrankDrink> {
        return competitionRepository.getById(competitionId).competitionDrankDrinks
    }

    @Transactional
    override fun create(competitionId: Long, request: CreateCompetitionDrinkRequest): CompetitionDrankDrink {

        val competition = competitionRepository.getById(competitionId)

        if (competition.isFinished) throw BadRequestException("Competition is already finished (closed)")

        val pubDrink = pubDrinkRepository.getById(request.pubDrinkId)
        var competitionDrankDrink = CompetitionDrankDrink().apply {
            this.competition = competition
            this.pubDrink = pubDrink
            this.user = auth.get()
        }
        competitionDrankDrink = competitionDrinkRepository.save(competitionDrankDrink)

        competition.competitionDrankDrinks.add(competitionDrankDrink)
        competitionRepository.save(competition)

        return competitionDrankDrink
    }

    @Transactional
    override fun delete(competitionId: Long, competitionDrinkId: Long) {
        competitionDrinkRepository.deleteById(competitionDrinkId)
    }
}