package de.vexxes.routes.penalty

import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getPenaltyById(
    app: Application,
    repository: Repository
) {
    get(Endpoint.GetPenaltyById.path) {
        try {

            app.log.info("PenaltyId " + call.parameters["penaltyId"])
            call.respond(
                message = repository.getPenaltyById(penaltyId = call.parameters["penaltyId"]),
                status = HttpStatusCode.OK
            )
        }
        catch (e: Exception) {
            app.log.info("GETTING PENALTY BY ID ERROR: ${e.message}")
            call.respond("GETTING PENALTY BY ID ERROR: ${e.message}")
        }
    }
}