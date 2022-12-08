package de.vexxes.routes.player

import de.vexxes.domain.model.ApiResponse
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deletePlayer(
    app: Application,
    repository: Repository
) {
    put(Endpoint.DeletePlayer.path) {
        try {
            val playerId = call.parameters["playerId"]
            val response = repository.deletePlayer(playerId = playerId)

            if (response) {
                call.respond(
                    message = ApiResponse(
                        success = true,
                        message = "Successfully deleted!"
                    ),
                    status = HttpStatusCode.OK
                )
            } else {
                call.respond(
                    message = ApiResponse(success = false),
                    status = HttpStatusCode.BadRequest
                )
            }
        } catch (e: Exception) {
            app.log.info("DELETE PLAYER INFO ERROR: $e")
        }
    }
}