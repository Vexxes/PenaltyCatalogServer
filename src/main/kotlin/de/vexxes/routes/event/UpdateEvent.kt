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

fun Route.updateEvent(
    app: Application,
    repository: EventRepository,
    validateBearerToken: ValidateBearerToken
) {
    put(Endpoint.UpdateEvent.path) {
        if (validateBearerToken.validateAdmin(call.request.headers["Authorization"].toString())) {
            try {
                val id = call.parameters["eventId"].toString()
                val eventRequest = call.receive<EventDto>()
                val event = eventRequest.toEvent()

                app.log.info("UPDATE EVENT INFO: $event")

                val updatedSuccessfully = repository.updateEvent(id, event)
                if (updatedSuccessfully) {
                    call.respond(
                        message = true,
                        status = HttpStatusCode.OK)
                } else {
                    call.respond(
                        message = false,
                        status = HttpStatusCode.BadRequest
                    )
                }
            } catch (e: Exception) {
                app.log.info("UPDATE EVENT INFO ERROR: ${e.message} ${e.cause}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}