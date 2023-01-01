package de.vexxes.routes.penaltyHistory

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.model.ApiResponse
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.model.PenaltyReceived
import de.vexxes.domain.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updatePenaltyHistory(
    app: Application,
    repository: Repository,
    validateBearerToken: ValidateBearerToken
) {
    put(Endpoint.UpdatePenaltyHistory.path) {
        if (validateBearerToken.validateAdmin(call.request.headers["Authorization"].toString())) {
            try {
                val penaltyReceived = call.receive<PenaltyReceived>()
                app.log.info("UPDATE PENALTY HISTORY INFO ERROR: $penaltyReceived")

                val response = repository.updatePenaltyHistory(penaltyReceived = penaltyReceived)

                if (response) {
                    call.respond(
                        message = ApiResponse(
                            success = true,
                            message = "Successfully Updated!"
                        ),
                        status = HttpStatusCode.OK
                    )
                } else {
                    call.respond(
                        message = ApiResponse(success = false),
                        status = HttpStatusCode.BadRequest
                    )
                }
            } catch (e: Exception) {
                app.log.info("UPDATE PENALTY HISTORY INFO ERROR: ${e.message} ${e.cause}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}