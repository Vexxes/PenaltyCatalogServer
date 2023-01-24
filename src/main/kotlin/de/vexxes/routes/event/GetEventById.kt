package de.vexxes.routes.event

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.extension.toDto
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.EventRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getEventById(
    app: Application,
    repository: EventRepository,
    validateBearerToken: ValidateBearerToken
) {
    get(Endpoint.GetEventById.path) {
        if (validateBearerToken.validateAll(call.request.headers["Authorization"].toString())) {
            try {
                val id = call.parameters["eventId"].toString()
                app.log.info("EventId " + call.parameters["eventId"])
                repository.getEventById(id)
                    ?.let { foundEvent ->
                        call.respond(
                            message = foundEvent.toDto(),
                            status = HttpStatusCode.OK
                        )
                    }
            } catch (e: Exception) {
                app.log.info("GETTING EVENTS BY ID ERROR: ${e.message}")
                call.respond("GETTING EVENTS BY ID ERROR: ${e.message}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}