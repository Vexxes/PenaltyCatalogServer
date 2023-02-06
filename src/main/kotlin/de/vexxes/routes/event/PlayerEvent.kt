package de.vexxes.routes.event

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.dto.PlayerStateDto
import de.vexxes.domain.extension.toPlayerState
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.EventRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.playerEvent(
    app: Application,
    repository: EventRepository,
    validateBearerToken: ValidateBearerToken
) {
    put(Endpoint.PlayerEvent.path) {
        if (validateBearerToken.validateAdmin(call.request.headers["Authorization"].toString())) {
            try {
                val id = call.parameters["eventId"].toString()
                val eventRequest = call.receive<PlayerStateDto>()
                val playerState = eventRequest.toPlayerState()

                app.log.info("UPDATE PLAYER EVENT INFO: $playerState")

                val updatedSuccessfully = repository.playerEvent(id, playerState)
                if (updatedSuccessfully) {
                    call.respond(
                        message = true,
                        status = HttpStatusCode.OK
                    )
                } else {
                    call.respond(
                        message = false,
                        status = HttpStatusCode.BadRequest
                    )
                }
            } catch (e: Exception) {
                app.log.info("PLAYER EVENT INFO ERROR: ${e.message} ${e.cause}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}