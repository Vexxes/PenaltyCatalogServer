package de.vexxes.routes.cancellation

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.extension.toDto
import de.vexxes.domain.model.Cancellation
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.CancellationRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getCancellationByPlayerId(
    app: Application,
    repository: CancellationRepository,
    validateBearerToken: ValidateBearerToken
) {
    get(Endpoint.GetCancellationByPlayerId.path) {
        if (validateBearerToken.validateAll(call.request.headers["Authorization"].toString())) {
            try {
                val id = call.parameters["playerId"].toString()
                val foundCancellation = repository.getCancellationForPlayer(id).map(Cancellation::toDto)
                call.respond(
                    message = foundCancellation,
                    status = HttpStatusCode.OK
                )
            } catch (e: Exception) {
                app.log.info("GETTING CANCELLATION BY SEARCH ERROR: ${e.message}")
                call.respond("GETTING CANCELLATION BY SEARCH ERROR: ${e.message}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}