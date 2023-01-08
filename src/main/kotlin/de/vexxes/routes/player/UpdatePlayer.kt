package de.vexxes.routes.player

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.dto.PlayerDto
import de.vexxes.domain.extension.toPlayer
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updatePlayer(
    app: Application,
    repository: Repository,
    validateBearerToken: ValidateBearerToken
) {
    put(Endpoint.UpdatePlayer.path) {
        if (validateBearerToken.validateAdmin(call.request.headers["Authorization"].toString())) {
            try {
                val id = call.parameters["playerId"].toString()
                val playerRequest = call.receive<PlayerDto>()
                val player = playerRequest.toPlayer()

                app.log.info("UPDATE PLAYER INFO: $player")

                val updatedSuccessfully = repository.updatePlayer(id, player)
                if (updatedSuccessfully) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Player update failed")
                }

            } catch (e: Exception) {
                app.log.info("UPDATE PLAYER INFO ERROR: ${e.message} ${e.cause}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}