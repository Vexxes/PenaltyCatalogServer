package de.vexxes.routes.penaltyHistory

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getPenaltyHistoryBySearch(
    app: Application,
    repository: Repository,
    validateBearerToken: ValidateBearerToken
) {
    get(Endpoint.GetPenaltyHistoryBySearch.path) {
        if (validateBearerToken.validateAll(call.request.headers["Authorization"].toString())) {
            try {
                call.respond(
                    message = repository.getPenaltyHistoryBySearch(searchText = call.request.queryParameters["searchText"]!!),
                    status = HttpStatusCode.OK
                )
            } catch (e: Exception) {
                app.log.info("GETTING PENALTY HISTORY BY SEARCH ERROR: ${e.message}")
                call.respond("GETTING PENALTY HISTORY BY SEARCH ERROR: ${e.message}")
            }
        }
    }
}