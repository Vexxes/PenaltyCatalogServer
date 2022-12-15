package de.vexxes.routes.penalty

import de.vexxes.domain.model.ApiResponse
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deletePenalty(
    app: Application,
    repository: Repository
) {
    put(Endpoint.DeletePenalty.path) {
        try {
            val penaltyId = call.parameters["penaltyId"]
            val response = repository.deletePenalty(penaltyId = penaltyId)

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
            app.log.info("DELETE PENALTY INFO ERROR: $e")
        }
    }
}