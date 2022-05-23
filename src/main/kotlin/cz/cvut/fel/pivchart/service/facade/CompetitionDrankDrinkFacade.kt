package cz.cvut.fel.pivchart.service.facade

import cz.cvut.fel.pivchart.utils.PubDrinkUtils
import cz.cvut.fel.pivchart.utils.UserUtils
import cz.cvut.fel.pivchart.model.entity.CompetitionDrankDrink
import cz.cvut.fel.pivchart.model.request.CreateCompetitionDrinkRequest
import cz.cvut.fel.pivchart.model.response.CompetitionDrankDrinkResponse
import cz.cvut.fel.pivchart.service.interfaces.CompetitionDrankDrinkServiceI
import cz.cvut.fel.pivchart.service.validate.CompetitionDrinkValidator
import org.springframework.stereotype.Service
import java.time.format.DateTimeFormatter
import java.util.stream.Collectors

/**
 * Competition drank drink service facade
 */
@Service
class CompetitionDrankDrinkFacade(
    private val competitionDrankDrinkService: CompetitionDrankDrinkServiceI,
    private val competitionDrinkValidator: CompetitionDrinkValidator
) {

    fun getAll(competitionId: Long): MutableList<CompetitionDrankDrinkResponse> {
        competitionDrinkValidator.validateOnGetAll(competitionId)
        val competitionDrankDrinks = competitionDrankDrinkService.getAll(competitionId)
        return competitionDrankDrinks.stream()
            .map {prepareResponse(it)}
            .collect(Collectors.toList())
    }

    fun create(competitionId: Long, request: CreateCompetitionDrinkRequest): CompetitionDrankDrinkResponse {
        competitionDrinkValidator.validateOnCreate(competitionId, request)

        val competitionDrankDrink = competitionDrankDrinkService.create(competitionId, request)
        return prepareResponse(competitionDrankDrink)
    }

    fun delete(competitionId: Long, competitionDrinkId: Long) {
        competitionDrinkValidator.validateOnDelete(competitionId, competitionDrinkId)
        competitionDrankDrinkService.delete(competitionId, competitionDrinkId)
    }

    private fun prepareResponse(competitionDrankDrink: CompetitionDrankDrink): CompetitionDrankDrinkResponse {
        return CompetitionDrankDrinkResponse(
            id = competitionDrankDrink.id!!,
            createdAt = competitionDrankDrink.createdAt.format(DateTimeFormatter.ISO_DATE_TIME),
            pubDrink = PubDrinkUtils.preparePubDrinkResponse(competitionDrankDrink.pubDrink),
            addedBy = UserUtils.prepareUserResponse(competitionDrankDrink.user)
        )
    }
}