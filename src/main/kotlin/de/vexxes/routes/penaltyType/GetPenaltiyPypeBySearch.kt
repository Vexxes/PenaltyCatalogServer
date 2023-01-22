package de.vexxes.routes.penaltyType

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.extension.toDto
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.model.PenaltyType
import de.vexxes.domain.repository.PenaltyTypeRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getPenaltyTypeBySearch(
    app: Application,
    repository: PenaltyTypeRepository,
    validateBearerToken: ValidateBearerToken
) {
    get(Endpoint.GetPenaltyTypesBySearch.path) {

        if (validateBearerToken.validateAll(call.request.headers["Authorization"].toString())) {
            try {
                val name = call.request.queryParameters["name"].toString()
                val foundPenaltyType = repository.getPenaltyTypeBySearch(name).map(PenaltyType::toDto)
                call.respond(
                    message = foundPenaltyType,
                    status = HttpStatusCode.OK
                )
            } catch (e: Exception) {
                app.log.info("GETTING PENALTIES BY SEARCH ERROR: ${e.message}")
                call.respond("GETTING PENALTIES BY SEARCH ERROR: ${e.message}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}