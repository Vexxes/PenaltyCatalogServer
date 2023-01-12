package de.vexxes.plugins

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.repository.PenaltyTypeRepository
import de.vexxes.domain.repository.Repository
import de.vexxes.routes.penaltyType.*
import de.vexxes.routes.penaltyHistory.*
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
        val repository: Repository by inject(Repository::class.java)
        val penaltyTypeRepository: PenaltyTypeRepository by inject(PenaltyTypeRepository::class.java)
        val validateBearerToken = ValidateBearerToken()
        rootRoute()

        getAllPlayer(application, repository, validateBearerToken)
        postPlayer(application, repository, validateBearerToken)
        getPlayerById(application, repository, validateBearerToken)
        getPlayersBySearch(application, repository, validateBearerToken)
        updatePlayer(application, repository, validateBearerToken)
        deletePlayer(application, repository, validateBearerToken)

        getAllPenaltyTypes(application, penaltyTypeRepository, validateBearerToken)
        getPenaltyTypeById(application, penaltyTypeRepository, validateBearerToken)
        postPenaltyType(application, penaltyTypeRepository, validateBearerToken)
        getDeclaredPenalties(application, repository, validateBearerToken)
        getPenaltyTypeBySearch(application, penaltyTypeRepository, validateBearerToken)
        updatePenalty(application, penaltyTypeRepository, validateBearerToken)
        deletePenaltyType(application, penaltyTypeRepository, validateBearerToken)

        getAllPenaltyHistory(application, repository, validateBearerToken)
        getPenaltyHistoryById(application, repository, validateBearerToken)
        getPenaltyHistoryBySearch(application, repository, validateBearerToken)
        updatePenaltyHistory(application, repository, validateBearerToken)
        deletePenaltyHistory(application, repository, validateBearerToken)

        unauthorizedRoute()
    }
}
