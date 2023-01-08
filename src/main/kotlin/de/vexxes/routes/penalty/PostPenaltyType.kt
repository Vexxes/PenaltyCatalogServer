package de.vexxes.routes.penalty

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

fun Route.postPenaltyType(
    app: Application,
    repository: PenaltyTypeRepository,
    validateBearerToken: ValidateBearerToken
) {
    post(Endpoint.PostPenaltyType.path) {
        if (validateBearerToken.validateAll(call.request.headers["Authorization"].toString())) {
            try {
                val request = call.receive<PenaltyTypeDto>()
                val penaltyType = request.toPenaltyType()

                repository.postPenaltyType(penaltyType)
                    ?.let { penaltyTypeId ->
                        call.respond(
                            message = penaltyTypeId.toString(),
                            status = HttpStatusCode.Created
                        )
                    } ?: call.respond(HttpStatusCode.BadRequest, "Bad request")
            } catch (e: Exception) {
                app.log.info("GETTING PLAYERS ERROR: ${e.message}")
                call.respond("GETTING PLAYERS ERROR: ${e.message}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}