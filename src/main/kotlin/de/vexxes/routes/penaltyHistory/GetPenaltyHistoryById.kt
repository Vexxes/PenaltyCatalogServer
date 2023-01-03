package de.vexxes.routes.penaltyHistory

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.toId

fun Route.getPenaltyHistoryById(
    app: Application,
    repository: Repository,
    validateBearerToken: ValidateBearerToken
) {
    get(Endpoint.GetPenaltyHistoryById.path) {
        if (validateBearerToken.validateAll(call.request.headers["Authorization"].toString())) {
            try {

                app.log.info("PenaltyHistoryId " + call.parameters["penaltyHistoryId"])
                call.respond(
                    message = repository.getPenaltyHistoryById(penaltyHistoryId = call.parameters["penaltyHistoryId"]!!.toId()),
                    status = HttpStatusCode.OK
                )
            } catch (e: Exception) {
                app.log.info("GETTING PENALTY HISTORY BY ID ERROR: ${e.message}")
                call.respond("GETTING PENALTY HISTORY BY ID ERROR: ${e.message}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}