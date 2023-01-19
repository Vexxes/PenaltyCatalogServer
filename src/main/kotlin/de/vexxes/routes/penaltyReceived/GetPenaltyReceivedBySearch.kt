package de.vexxes.routes.penaltyReceived

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.PlayerRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getPenaltyHistoryBySearch(
    app: Application,
    repository: PlayerRepository,
    validateBearerToken: ValidateBearerToken
) {
    get(Endpoint.GetPenaltyHistoryBySearch.path) {
        if (validateBearerToken.validateAll(call.request.headers["Authorization"].toString())) {
            try {

            } catch (e: Exception) {
                app.log.info("GETTING PENALTY RECEIVED BY SEARCH ERROR: ${e.message}")
                call.respond("GETTING PENALTY RECEIVED BY SEARCH ERROR: ${e.message}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}