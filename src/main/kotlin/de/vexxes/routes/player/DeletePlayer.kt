package de.vexxes.routes.player

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.model.ApiResponse
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.toId

fun Route.deletePlayer(
    app: Application,
    repository: Repository,
    validateBearerToken: ValidateBearerToken
) {
    put(Endpoint.DeletePlayer.path) {

        if (validateBearerToken.validateAdmin(call.request.headers["Authorization"].toString())) {
            try {
                val playerId = call.parameters["playerId"]
                val response = repository.deletePlayer(playerId = playerId!!.toId())

                if (response) {
                    call.respond(
                        message = ApiResponse(
                            success = true,
                            message = "Successfully deleted!"
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
                app.log.info("DELETE PLAYER INFO ERROR: $e")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}