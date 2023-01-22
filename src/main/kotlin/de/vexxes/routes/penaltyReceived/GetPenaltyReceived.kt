package de.vexxes.routes.penaltyReceived

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.extension.toDto
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.model.PenaltyReceived
import de.vexxes.domain.repository.PenaltyReceivedRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllPenaltyReceived(
    app: Application,
    repository: PenaltyReceivedRepository,
    validateBearerToken: ValidateBearerToken
) {
    get(Endpoint.GetAllPenaltyReceived.path) {
        if (validateBearerToken.validateAll(call.request.headers["Authorization"].toString())) {
            try {
                call.respond(
                    message = repository.getAllPenaltyReceived().map(PenaltyReceived::toDto),
                    status = HttpStatusCode.OK
                )
            } catch (e: Exception) {
                app.log.info("GETTING PENALTY RECEIVED ERROR: ${e.message}")
                call.respond("GETTING PENALTY RECEIVED ERROR: ${e.message}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}