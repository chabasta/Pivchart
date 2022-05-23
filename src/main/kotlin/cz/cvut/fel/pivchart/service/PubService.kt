package cz.cvut.fel.pivchart.service

import cz.cvut.fel.pivchart.model.entity.Pub
import cz.cvut.fel.pivchart.model.entity.PubOwner
import cz.cvut.fel.pivchart.model.entity.embeddable.Address
import cz.cvut.fel.pivchart.model.request.CreatePubRequest
import cz.cvut.fel.pivchart.model.request.IndexPubsRequest
import cz.cvut.fel.pivchart.model.request.UpdatePubRequest
import cz.cvut.fel.pivchart.repository.PubOwnerRepository
import cz.cvut.fel.pivchart.repository.PubRepository
import cz.cvut.fel.pivchart.repository.UserRepository
import cz.cvut.fel.pivchart.service.interfaces.PubServiceI
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Pub service
 */
@Service
class PubService(
    private val pubRepository: PubRepository,
    private val pubOwnerRepository: PubOwnerRepository,
    private val userRepository: UserRepository
): PubServiceI {
    @Transactional
    override fun getAll(request: IndexPubsRequest): List<Pub> {
        return pubRepository.filter(request)
    }

    @Transactional
    override fun get(pubId: Long): Pub {
        return pubRepository.getById(pubId)
    }

    @Transactional
    override fun create(request: CreatePubRequest): Pub {

        val owner = PubOwner().apply {
            this.user = userRepository.getById(request.pubOwner)
        }

        pubOwnerRepository.save(owner)

        val pub = Pub().apply {
            this.name = request.name
            this.address = Address().apply {
                city = request.city
                street = request.street
                country = request.country
                postalCode = request.postalCode
            }
            this.latitude = request.latitude
            this.longitude = request.longitude
            this.pubOwner = owner
        }

        owner.pubs.add(pub)
        pubOwnerRepository.save(owner)

        return pubRepository.save(pub)
    }

    @Transactional
    override fun update(pubId: Long, request: UpdatePubRequest): Pub {


        val pub = pubRepository.getById(pubId)
        val address = pub.address.apply {
            city = request.city
            street = request.street
            country = request.country
            postalCode = request.postalCode
        }

        pub.apply {
            this.name = request.name
            this.address = address
            this.latitude = request.latitude
            this.longitude = request.longitude
        }

        return pubRepository.save(pub)
    }

    @Transactional
    override fun delete(pubId: Long) {
        pubRepository.deleteById(pubId)
    }
}