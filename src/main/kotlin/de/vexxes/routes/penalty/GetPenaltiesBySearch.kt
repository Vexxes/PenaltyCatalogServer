package de.vexxes.routes.penalty

import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getPenaltiesBySearch(
    app: Application,
    repository: Repository
) {
    get(Endpoint.GetPenaltiesBySearch.path) {
        try {
            call.respond(
                message = repository.getPenaltiesBySearch(searchText = call.request.queryParameters["searchText"]!!),
                status = HttpStatusCode.OK
            )
        }
        catch (e: Exception) {
            app.log.info("GETTING PENALTIES BY SEARCH ERROR: ${e.message}")
            call.respond("GETTING PENALTIES BY SEARCH ERROR: ${e.message}")
        }
    }
}