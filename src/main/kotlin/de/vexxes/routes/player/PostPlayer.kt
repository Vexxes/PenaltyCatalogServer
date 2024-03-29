package de.vexxes.routes.player

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.dto.PlayerDto
import de.vexxes.domain.extension.toPlayer
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.PlayerRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.postPlayer(
    app: Application,
    repository: PlayerRepository,
    validateBearerToken: ValidateBearerToken
) {
    post(Endpoint.PostPlayer.path) {
        if (validateBearerToken.validateAll(call.request.headers["Authorization"].toString())) {
            try {
                val request = call.receive<PlayerDto>()
                val player = request.toPlayer()

                repository.postPlayer(player)
                    ?.let { userId ->
                        call.respond(
                            message = userId.toString(),
                            status = HttpStatusCode.OK
                        )
                    } ?: call.respond(HttpStatusCode.BadRequest, "Bad request")
            } catch (e: Exception) {
                app.log.info("POST PLAYERS ERROR: ${e.message}")
                call.respond("POST PLAYERS ERROR: ${e.message}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}