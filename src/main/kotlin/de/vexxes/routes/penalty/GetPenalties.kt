package de.vexxes.routes.penalty

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllPenalties(
    app: Application,
    repository: Repository,
    validateBearerToken: ValidateBearerToken
) {
    get(Endpoint.GetAllPenalties.path) {

        if (validateBearerToken.validateAll(call.request.headers["Authorization"].toString())) {
            try {
                call.respond(
                    message = repository.getAllPenalties(),
                    status = HttpStatusCode.OK
                )
            } catch (e: Exception) {
                app.log.info("GETTING PENALTIES ERROR: ${e.message}")
                call.respond("GETTING PENALTIES ERROR: ${e.message}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}