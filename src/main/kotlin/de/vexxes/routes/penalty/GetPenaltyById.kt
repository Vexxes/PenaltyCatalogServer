package de.vexxes.routes.penalty

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.toId

fun Route.getPenaltyById(
    app: Application,
    repository: Repository,
    validateBearerToken: ValidateBearerToken
) {
    get(Endpoint.GetPenaltyById.path) {

        if (validateBearerToken.validateAll(call.request.headers["Authorization"].toString())) {
            try {

                app.log.info("PenaltyId " + call.parameters["penaltyId"])
                call.respond(
                    message = repository.getPenaltyById(penaltyId = call.parameters["penaltyId"]!!.toId()),
                    status = HttpStatusCode.OK
                )
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