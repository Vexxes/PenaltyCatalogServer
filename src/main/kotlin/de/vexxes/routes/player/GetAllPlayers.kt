package de.vexxes.routes.player

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.extension.toDto
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.model.Player
import de.vexxes.domain.repository.PlayerRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllPlayer(
    app: Application,
    repository: PlayerRepository,
    validateBearerToken: ValidateBearerToken
) {
    get(Endpoint.GetAllPlayers.path) {
        if (validateBearerToken.validateAll(call.request.headers["Authorization"].toString())) {
            try {
                call.respond(
                    message = repository.getAllPlayers().map(Player::toDto),
                    status = HttpStatusCode.OK
                )
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