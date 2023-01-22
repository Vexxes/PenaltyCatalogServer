package de.vexxes.routes.penaltyReceived

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.extension.toDto
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.PenaltyReceivedRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getPenaltyReceivedById(
    app: Application,
    repository: PenaltyReceivedRepository,
    validateBearerToken: ValidateBearerToken
) {
    get(Endpoint.GetPenaltyReceivedById.path) {
        if (validateBearerToken.validateAll(call.request.headers["Authorization"].toString())) {
            try {
                val id = call.parameters["penaltyReceivedId"].toString()
                repository.getPenaltyReceivedById(id)
                    ?.let { penaltyReceived ->
                        call.respond(
                            message = penaltyReceived.toDto(),
                            status = HttpStatusCode.OK
                        )
                    }
            } catch (e: Exception) {
                app.log.info("GETTING PENALTY RECEIVED BY ID ERROR: ${e.message}")
                call.respond("GETTING PENALTY RECEIVED BY ID ERROR: ${e.message}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}