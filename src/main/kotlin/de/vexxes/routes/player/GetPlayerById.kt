package de.vexxes.routes.player

import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getPlayerById(
    app: Application,
    repository: Repository
) {
    get(Endpoint.GetPlayerById.path) {
        try {

            app.log.info("PlayerId " + call.parameters["playerId"])
            call.respond(
                message = repository.getPlayerById(playerId = call.parameters["playerId"]),
                status = HttpStatusCode.OK
            )
        }
        catch (e: Exception) {
            app.log.info("GETTING PLAYER BY ID ERROR: ${e.message}")
            call.respond("GETTING PLAYER BY ID ERROR: ${e.message}")
        }
    }
}