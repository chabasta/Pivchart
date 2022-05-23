package cz.cvut.fel.pivchart.service.facade

import cz.cvut.fel.pivchart.utils.CompetitionUtils
import cz.cvut.fel.pivchart.model.entity.Competition
import cz.cvut.fel.pivchart.model.request.CreateCompetitionRequest
import cz.cvut.fel.pivchart.model.request.UpdateCompetitionRequest
import cz.cvut.fel.pivchart.model.response.CompetitionResponse
import cz.cvut.fel.pivchart.service.interfaces.CompetitionServiceI
import cz.cvut.fel.pivchart.service.validate.CompetitionValidator
import org.springframework.stereotype.Service
import java.util.stream.Collectors

/**
 * Competition service facade
 */
@Service
class CompetitionFacade(
    private val competitionService: CompetitionServiceI,
    private val competitionValidator: CompetitionValidator
) {
    fun getAll(): MutableList<CompetitionResponse> {
        val competitions = competitionService.getAll()

        return competitions.stream()
            .map {prepareCompetitionResponse(it)}
            .collect(Collectors.toList())
    }

    fun get(competitionId: Long): CompetitionResponse {
        competitionValidator.validateOnGet(competitionId)
        val competition = competitionService.get(competitionId)

        return prepareCompetitionResponse(competition)
    }

    fun create(request: CreateCompetitionRequest): CompetitionResponse {
        competitionValidator.validateOnCreate(request)
        val competition = competitionService.create(request)

        return prepareCompetitionResponse(competition)
    }

    fun update(competitionId: Long, request: UpdateCompetitionRequest): CompetitionResponse {
        competitionValidator.validateOnUpdate(competitionId, request)
        val competition = competitionService.update(competitionId, request)

        return prepareCompetitionResponse(competition)
    }

    fun delete(competitionId: Long) {
        competitionValidator.validateOnDelete(competitionId)
        competitionService.delete(competitionId)
    }

    fun finish(competitionId: Long): CompetitionResponse {
        competitionValidator.validateOnFinish(competitionId)
        val competition = competitionService.finish(competitionId)

        return prepareCompetitionResponse(competition)
    }

    private fun prepareCompetitionResponse(competition: Competition): CompetitionResponse {
        return CompetitionUtils.prepareCompetitionResponse(competition)
    }
}