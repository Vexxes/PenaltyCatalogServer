package de.vexxes.plugins

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.repository.Repository
import de.vexxes.routes.penalty.*
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
        val validateBearerToken = ValidateBearerToken()
        rootRoute()
        getAllPlayer(application, repository, validateBearerToken)
        getPlayerById(application, repository, validateBearerToken)
        getPlayersBySearch(application, repository, validateBearerToken)
        updatePlayer(application, repository, validateBearerToken)
        deletePlayer(application, repository, validateBearerToken)
        getAllCategories(application, repository, validateBearerToken)
        getAllPenalties(application, repository, validateBearerToken)
        getPenaltyById(application, repository, validateBearerToken)
        getPenaltiesBySearch(application, repository, validateBearerToken)
        updatePenalty(application, repository, validateBearerToken)
        deletePenalty(application, repository, validateBearerToken)

        unauthorizedRoute()
    }
}
