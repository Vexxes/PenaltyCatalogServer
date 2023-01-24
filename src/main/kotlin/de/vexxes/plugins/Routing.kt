package de.vexxes.plugins

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.repository.EventRepository
import de.vexxes.domain.repository.PenaltyReceivedRepository
import de.vexxes.domain.repository.PenaltyTypeRepository
import de.vexxes.domain.repository.PlayerRepository
import de.vexxes.routes.event.*
import de.vexxes.routes.penaltyReceived.*
import de.vexxes.routes.penaltyType.*
import de.vexxes.routes.player.*
import de.vexxes.routes.rootRoute
import de.vexxes.routes.unauthorizedRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Application.configureRouting() {
//    install(Locations) {
//    }

    routing {
        val playerRepository: PlayerRepository by inject(PlayerRepository::class.java)
        val penaltyTypeRepository: PenaltyTypeRepository by inject(PenaltyTypeRepository::class.java)
        val penaltyReceivedRepository: PenaltyReceivedRepository by inject(PenaltyReceivedRepository::class.java)
        val eventRepository: EventRepository by inject(EventRepository::class.java)
        val validateBearerToken = ValidateBearerToken()
        rootRoute()

        getAllPlayer(application, playerRepository, validateBearerToken)
        postPlayer(application, playerRepository, validateBearerToken)
        getPlayerById(application, playerRepository, validateBearerToken)
        getPlayersBySearch(application, playerRepository, validateBearerToken)
        updatePlayer(application, playerRepository, validateBearerToken)
        deletePlayer(application, playerRepository, validateBearerToken)

        getAllPenaltyTypes(application, penaltyTypeRepository, validateBearerToken)
        getPenaltyTypeById(application, penaltyTypeRepository, validateBearerToken)
        postPenaltyType(application, penaltyTypeRepository, validateBearerToken)
        getDeclaredPenalties(application, playerRepository, validateBearerToken)
        getPenaltyTypeBySearch(application, penaltyTypeRepository, validateBearerToken)
        updatePenalty(application, penaltyTypeRepository, validateBearerToken)
        deletePenaltyType(application, penaltyTypeRepository, validateBearerToken)

        getAllPenaltyReceived(application, penaltyReceivedRepository, validateBearerToken)
        getPenaltyReceivedById(application, penaltyReceivedRepository, validateBearerToken)
        postPenaltyReceived(application, penaltyReceivedRepository, validateBearerToken)
        getPenaltyReceivedByPlayerId(application, penaltyReceivedRepository, validateBearerToken)
        updatePenaltyReceived(application, penaltyReceivedRepository, validateBearerToken)
        deletePenaltyReceived(application, penaltyReceivedRepository, validateBearerToken)

        getAllEvents(application, eventRepository, validateBearerToken)
        getEventById(application, eventRepository, validateBearerToken)
        postEvent(application, eventRepository, validateBearerToken)
        updateEvent(application, eventRepository, validateBearerToken)
        deleteEvent(application, eventRepository, validateBearerToken)

        unauthorizedRoute()
    }
}
