package de.vexxes.routes.player

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.extension.toDto
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.PlayerRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getPlayerById(
    app: Application,
    repository: PlayerRepository,
    validateBearerToken: ValidateBearerToken
) {
    get(Endpoint.GetPlayerById.path) {
        if (validateBearerToken.validateAll(call.request.headers["Authorization"].toString())) {
            try {
                val id = call.parameters["playerId"].toString()
                app.log.info("PlayerId " + call.parameters["playerId"])
                repository.getPlayerById(id)
                    ?.let { foundPerson ->
                        call.respond(
                            message = foundPerson.toDto(),
                            status = HttpStatusCode.OK
                        )
                    }
                    ?: call.respond(HttpStatusCode.NotFound, "No Player Found")
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