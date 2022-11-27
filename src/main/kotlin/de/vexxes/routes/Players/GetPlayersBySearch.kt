package de.vexxes.routes.Players

import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getPlayersBySearch(
    app: Application,
    repository: Repository
) {
    get(Endpoint.GetPlayersBySearch.path) {
        try {
            call.respond(
                message = repository.getPlayersBySearch(searchText = call.request.queryParameters["searchText"]!!),
                status = HttpStatusCode.OK
            )
        }
        catch (e: Exception) {
            app.log.info("GETTING PLAYERS BY SEARCH ERROR: ${e.message}")
            call.respond("GETTING PLAYERS BY SEARCH ERROR: ${e.message}")
        }
    }
}