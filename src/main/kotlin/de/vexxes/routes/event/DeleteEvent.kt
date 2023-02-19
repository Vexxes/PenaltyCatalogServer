package de.vexxes.routes.event

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.EventRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteEvent(
    app: Application,
    repository: EventRepository,
    validateBearerToken: ValidateBearerToken
) {
    delete(Endpoint.DeleteEvent.path) {
        if (validateBearerToken.validateAdmin(call.request.headers["Authorization"].toString())) {
            try {
                val id = call.parameters["eventId"].toString()

                val deleteSuccessfully = repository.deleteEvent(id)

                if (deleteSuccessfully) {
                    call.respond(
                        message = true,
                        status = HttpStatusCode.OK
                    )
                } else {
                    call.respond(
                        message = false,
                        status = HttpStatusCode.NotFound
                    )
                }

            } catch (e: Exception) {
                app.log.info("DELETE EVENT INFO ERROR: $e")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}