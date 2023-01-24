package de.vexxes.routes.event

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.extension.toDto
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.model.Event
import de.vexxes.domain.repository.EventRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllEvents(
    app: Application,
    repository: EventRepository,
    validateBearerToken: ValidateBearerToken
) {
    get(Endpoint.GetAllEvents.path) {
        if (validateBearerToken.validateAll(call.request.headers["Authorization"].toString())) {
            try {
                call.respond(
                    message = repository.getAllEvents().map(Event::toDto),
                    status = HttpStatusCode.OK
                )
            } catch (e: Exception) {
                app.log.info("GETTING EVENTS ERROR: ${e.message}")
                call.respond("GETTING EVENTS ERROR: ${e.message}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}