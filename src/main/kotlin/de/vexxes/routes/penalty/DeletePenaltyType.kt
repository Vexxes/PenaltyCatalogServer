package de.vexxes.routes.penalty

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.PenaltyTypeRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deletePenaltyType(
    app: Application,
    repository: PenaltyTypeRepository,
    validateBearerToken: ValidateBearerToken
) {

    delete(Endpoint.DeletePenalty.path) {
        if (validateBearerToken.validateAdmin(call.request.headers["Authorization"].toString())) {
            try {
                val id = call.parameters["penaltyTypeId"].toString()
                val deleteSuccessFully = repository.deletePenaltyType(id)
                if (deleteSuccessFully) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound, "PenaltyType with id $id not found")
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