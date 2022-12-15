package de.vexxes.routes.player

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllPlayer(
    app: Application,
    repository: Repository,
    validateBearerToken: ValidateBearerToken
) {
    get(Endpoint.GetAllPlayers.path) {
        if (validateBearerToken.validateAll(call.request.headers["Authorization"].toString())) {
            try {
                val sortAscDesc =
                    if (call.request.queryParameters["sortAscDesc"].isNullOrEmpty()) 1 else call.request.queryParameters["sortAscDesc"]!!.toInt()
                println(sortAscDesc)
                call.respond(
                    message = repository.getAllPlayers(sortOrder = sortAscDesc),
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