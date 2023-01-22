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

fun Route.updatePenaltyReceived(
    app: Application,
    repository: PenaltyReceivedRepository,
    validateBearerToken: ValidateBearerToken
) {
    put(Endpoint.UpdatePenaltyReceived.path) {

        if (validateBearerToken.validateAdmin(call.request.headers["Authorization"].toString())) {
            try {
                val id = call.parameters["penaltyReceivedId"].toString()
                val penaltyReceivedRequest = call.receive<PenaltyReceivedDto>()
                val penaltyReceived = penaltyReceivedRequest.toPenaltyReceived()

                val updatedSuccessfully = repository.updatePenaltyReceived(id, penaltyReceived)
                if (updatedSuccessfully) {
                    call.respond(
                        message = true,
                        status = HttpStatusCode.OK
                    )
                } else {
                    call.respond(
                        message = false,
                        status = HttpStatusCode.BadRequest
                    )
                }
            } catch (e: Exception) {
                app.log.info("UPDATE PENALTY RECEIVED INFO ERROR: ${e.message} ${e.cause}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}