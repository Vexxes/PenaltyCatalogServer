package de.vexxes.routes.penalty

import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllPenalties(
    app: Application,
    repository: Repository
) {
    get(Endpoint.GetAllPenalties.path) {
        try {
            call.respond(
                message = repository.getAllPenalties(),
                status = HttpStatusCode.OK
            )
        }
        catch (e: Exception) {
            app.log.info("GETTING PENALTIES ERROR: ${e.message}")
            call.respond("GETTING PENALTIES ERROR: ${e.message}")
        }
    }
}