package de.vexxes.routes.penaltyType

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.repository.PlayerRepository
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.getDeclaredPenalties(
    app: Application,
    repository: PlayerRepository,
    validateBearerToken: ValidateBearerToken
) {
    /*
    get(Endpoint.GetDeclaredPenalties.path) {

        if (validateBearerToken.validateAll(call.request.headers["Authorization"].toString())) {
            try {
                call.respond(
                    message = repository.getDeclaredPenalties(penaltyId = call.request.queryParameters["penaltyName"]!!.toId()),
                    status = HttpStatusCode.OK
                )
            } catch (e: Exception) {
                app.log.info("GETTING DECLARED PENALTIES ERROR: ${e.message}")
                call.respond("GETTING DECLARED PENALTIES ERROR: ${e.message}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
     */
}