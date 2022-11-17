package de.vexxes.routes

import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllPlayer(
    app: Application,
    repository: Repository
) {
    get(Endpoint.GetAllPlayers.path) {
        try {
            call.respond(
                message = repository.getAllPlayers(),
//                message = ApiResponse(
//                    success = true,
//                    player = repository.getAllPlayers()
//                ),
                status = HttpStatusCode.OK
            )
        }
        catch (e: Exception) {
            app.log.info("GETTING PLAYERS ERROR: ${e.message}")
            call.respond("GETTING PLAYERS ERROR: ${e.message}")
//            call.respondRedirect(Endpoint.Unauthorized.path)
        }
    }
}