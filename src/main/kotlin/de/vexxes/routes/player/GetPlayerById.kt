package de.vexxes.routes.player

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.toId

fun Route.getPlayerById(
    app: Application,
    repository: Repository,
    validateBearerToken: ValidateBearerToken
) {
    get(Endpoint.GetPlayerById.path) {
        if (validateBearerToken.validateAll(call.request.headers["Authorization"].toString())) {
            try {

                app.log.info("PlayerId " + call.parameters["playerId"])
                call.respond(
                    message = repository.getPlayerById(playerId = call.parameters["playerId"]!!.toId()),
                    status = HttpStatusCode.OK
                )
            } catch (e: Exception) {
                app.log.info("GETTING PLAYER BY ID ERROR: ${e.message}")
                call.respond("GETTING PLAYER BY ID ERROR: ${e.message}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}