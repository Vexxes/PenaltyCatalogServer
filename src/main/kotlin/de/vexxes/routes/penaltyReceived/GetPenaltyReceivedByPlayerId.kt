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

fun Route.getPenaltyReceivedByPlayerId(
    app: Application,
    repository: PenaltyReceivedRepository,
    validateBearerToken: ValidateBearerToken
) {
    get(Endpoint.GetPenaltyReceivedByPlayerId.path) {
        if (validateBearerToken.validateAll(call.request.headers["Authorization"].toString())) {
            try {
                val id = call.parameters["playerId"].toString()
                val foundPenaltyReceived = repository.getPenaltyReceivedForPlayer(id).map(PenaltyReceived::toDto)
                call.respond(
                    message = foundPenaltyReceived,
                    status = HttpStatusCode.OK
                )
            } catch (e: Exception) {
                app.log.info("GETTING PENALTY RECEIVED BY SEARCH ERROR: ${e.message}")
                call.respond("GETTING PENALTY RECEIVED BY SEARCH ERROR: ${e.message}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}