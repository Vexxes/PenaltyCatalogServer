package de.vexxes.routes.penaltyHistory

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.model.ApiResponse
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deletePenaltyHistory(
    app: Application,
    repository: Repository,
    validateBearerToken: ValidateBearerToken
) {
    put(Endpoint.DeletePenaltyHistory.path) {
        if (validateBearerToken.validateAdmin(call.request.headers["Authorization"].toString())) {
            try {
                val penaltyHistoryId = call.parameters["penaltyHistoryId"]
                val response = repository.deletePenaltyHistory(penaltyHistoryId = penaltyHistoryId)

                if (response) {
                    call.respond(
                        message = ApiResponse(
                            success = true,
                            message = "Successfully deleted!"
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
                app.log.info("DELETE PENALTY HISTORY INFO ERROR: $e")
            }
        }
    }
}