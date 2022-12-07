package de.vexxes.routes

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.model.ApiResponse
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.model.Player
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
                val player = call.receive<Player>()
                app.log.info("UPDATE PLAYER INFO ERROR: $player")

                val response = repository.updatePlayer(player = player)

                if (response) {
                    call.respond(
                        message = ApiResponse(
                            success = true,
                            message = "Successfully Updated!"
                        ),
                        status = HttpStatusCode.OK
                    )
                } else {
                    call.respond(
                        message = ApiResponse(success = false),
                        status = HttpStatusCode.BadRequest
                    )
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