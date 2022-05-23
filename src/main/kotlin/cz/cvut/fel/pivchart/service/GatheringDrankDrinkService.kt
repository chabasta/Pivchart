package cz.cvut.fel.pivchart.service

import cz.cvut.fel.pivchart.utils.Auth
import cz.cvut.fel.pivchart.exceptions.api.BadRequestException
import cz.cvut.fel.pivchart.model.entity.GatheringDrankDrink
import cz.cvut.fel.pivchart.model.request.CreateGatheringDrinkRequest
import cz.cvut.fel.pivchart.repository.GatheringDrinkRepository
import cz.cvut.fel.pivchart.repository.GatheringRepository
import cz.cvut.fel.pivchart.repository.PubDrinkRepository
import cz.cvut.fel.pivchart.service.interfaces.GatheringDrankDrinkServiceI
import org.springframework.stereotype.Service
import javax.transaction.Transactional

/**
 * Gathering drank drink service
 */
@Service
class GatheringDrankDrinkService (
    private val gatheringRepository: GatheringRepository,
    private val pubDrinkRepository: PubDrinkRepository,
    private val gatheringDrinkRepository: GatheringDrinkRepository,
    private val auth: Auth
): GatheringDrankDrinkServiceI {
    @Transactional
    override fun getAll(gatheringId: Long): MutableList<GatheringDrankDrink> {
        return gatheringRepository.getById(gatheringId).gatheringDrankDrinks
    }

    @Transactional
    override fun create(gatheringId: Long, request: CreateGatheringDrinkRequest): GatheringDrankDrink {
        val gathering = gatheringRepository.getById(gatheringId)

        if (gathering.isFinished) throw BadRequestException("Gathering is already finished (closed).")

        val pubDrink = pubDrinkRepository.getById(request.pubDrinkId)
        var gatheringDrankDrink = GatheringDrankDrink().apply {
            this.gathering = gathering
            this.pubDrink = pubDrink
            this.user = auth.get()
        }
        gatheringDrankDrink = gatheringDrinkRepository.save(gatheringDrankDrink)

        gathering.gatheringDrankDrinks.add(gatheringDrankDrink)
        gatheringRepository.save(gathering)

        return gatheringDrankDrink
    }

    @Transactional
    override fun delete(gatheringId: Long, gatheringDrinkId: Long) {
        gatheringDrinkRepository.deleteById(gatheringDrinkId)
    }
}