package de.vexxes.routes.penaltyReceived

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.dto.PenaltyReceivedDto
import de.vexxes.domain.extension.toPenaltyReceived
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.PenaltyReceivedRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.postPenaltyReceived(
    app: Application,
    repository: PenaltyReceivedRepository,
    validateBearerToken: ValidateBearerToken
) {
    post(Endpoint.PostPenaltyReceived.path) {
        if (validateBearerToken.validateAll(call.request.headers["Authorization"].toString())) {
            try {
                val request = call.receive<PenaltyReceivedDto>()
                val penaltyReceived = request.toPenaltyReceived()
                println(penaltyReceived)

                repository.postPenaltyReceived(penaltyReceived)
                    ?.let { penaltyReceivedId ->
                        call.respond(
                            message = penaltyReceivedId.toString(),
                            status = HttpStatusCode.Created
                        )
                    } ?: call.respond(HttpStatusCode.BadRequest, "Bad request")
            } catch (e: Exception) {
                app.log.info("POST PENALTY RECEIVED ERROR: ${e.message}")
                call.respond("POST PENALTY RECEIVED ERROR: ${e.message}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}