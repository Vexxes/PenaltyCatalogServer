package de.vexxes.routes.penaltyType

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.dto.PenaltyTypeDto
import de.vexxes.domain.extension.toPenaltyType
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.PenaltyTypeRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updatePenalty(
    app: Application,
    repository: PenaltyTypeRepository,
    validateBearerToken: ValidateBearerToken
) {
    put(Endpoint.UpdatePenaltyType.path) {

        if (validateBearerToken.validateAdmin(call.request.headers["Authorization"].toString())) {
            try {
                val id = call.parameters["penaltyTypeId"].toString()
                val penaltyTypeRequest = call.receive<PenaltyTypeDto>()
                val penaltyType = penaltyTypeRequest.toPenaltyType()

                val updatedSuccessfully = repository.updatePenaltyType(id, penaltyType)
                if (updatedSuccessfully) {
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.BadRequest, "PenaltyType update failed")
                }
            } catch (e: Exception) {
                app.log.info("UPDATE PENALTY INFO ERROR: ${e.message} ${e.cause}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}