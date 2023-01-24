package de.vexxes.routes.event

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.dto.EventDto
import de.vexxes.domain.extension.toEvent
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.EventRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.postEvent(
    app: Application,
    repository: EventRepository,
    validateBearerToken: ValidateBearerToken
) {
    post(Endpoint.PostEvent.path) {
        if (validateBearerToken.validateAll(call.request.headers["Authorization"].toString())) {
            try {
                val request = call.receive<EventDto>()
                val event = request.toEvent()

                repository.postEvent(event)
                    ?.let { eventId ->
                        call.respond(
                            message = eventId.toString(),
                            status = HttpStatusCode.OK
                        )
                    } ?: call.respond(HttpStatusCode.BadRequest, "Bad request")
            } catch (e: Exception) {
                app.log.info("POST EVENTS ERROR: ${e.message}")
                call.respond("POST EVENTS ERROR: ${e.message}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}