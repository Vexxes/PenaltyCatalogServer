package de.vexxes.routes.player

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deletePlayer(
    app: Application,
    repository: Repository,
    validateBearerToken: ValidateBearerToken
) {
    delete(Endpoint.DeletePlayer.path) {

        if (validateBearerToken.validateAdmin(call.request.headers["Authorization"].toString())) {
            try {
                val id = call.parameters["playerId"].toString()

                val deleteSuccessfully = repository.deletePlayer(id)

                if (deleteSuccessfully) {
                    call.respond(
                        message = true,
                        status = HttpStatusCode.OK
                    )
                } else {
                    call.respond(HttpStatusCode.NotFound, "Player with id $id not found")
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