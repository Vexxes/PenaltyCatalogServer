package de.vexxes.plugins

import de.vexxes.domain.repository.Repository
import de.vexxes.routes.*
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
        updatePlayer(application, repository)
        deletePlayer(application, repository)
        unauthorizedRoute()
    }
}
