package de.vexxes.routes.penalty

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.model.ApiResponse
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.toId

fun Route.deletePenalty(
    app: Application,
    repository: Repository,
    validateBearerToken: ValidateBearerToken
) {

    put(Endpoint.DeletePenalty.path) {
        if (validateBearerToken.validateAdmin(call.request.headers["Authorization"].toString())) {
            try {
                val penaltyId = call.parameters["penaltyId"]
                val response = repository.deletePenalty(penaltyId = penaltyId!!.toId())

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
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}