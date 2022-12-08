package de.vexxes.plugins

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
        rootRoute()

        getAllPlayer(application, repository)
        getPlayerById(application, repository)
        getPlayersBySearch(application, repository)
        updatePlayer(application, repository)
        deletePlayer(application, repository)
        getAllCategories(application, repository)
        getAllCategories(application, repository)
        getAllPenalties(application, repository)
        getPenaltyById(application, repository)
        getPenaltiesBySearch(application, repository)
        updatePenalty(application, repository)
        deletePenalty(application, repository)

        unauthorizedRoute()
    }
}
