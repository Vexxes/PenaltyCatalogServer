package de.vexxes.routes.penaltyType

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.extension.toDto
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.PenaltyTypeRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getPenaltyTypeById(
    app: Application,
    repository: PenaltyTypeRepository,
    validateBearerToken: ValidateBearerToken
) {
    get(Endpoint.GetPenaltyTypeById.path) {

        if (validateBearerToken.validateAll(call.request.headers["Authorization"].toString())) {
            try {
                val id = call.parameters["penaltyTypeId"].toString()
                repository.getPenaltyTypeById(id)
                    ?.let { penaltyType ->
                        call.respond(
                            message = penaltyType.toDto(),
                            status = HttpStatusCode.OK
                        )
                    }
            } catch (e: Exception) {
                app.log.info("GETTING PENALTY BY ID ERROR: ${e.message}")
                call.respond("GETTING PENALTY BY ID ERROR: ${e.message}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}