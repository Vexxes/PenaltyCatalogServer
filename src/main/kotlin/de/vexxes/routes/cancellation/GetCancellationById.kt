package de.vexxes.routes.cancellation

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.extension.toDto
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.CancellationRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getCancellationById(
    app: Application,
    repository: CancellationRepository,
    validateBearerToken: ValidateBearerToken
) {
    get(Endpoint.GetCancellationById.path) {
        if (validateBearerToken.validateAll(call.request.headers["Authorization"].toString())) {
            try {
                val id = call.parameters["cancellationId"].toString()
                repository.getCancellationById(id)
                    ?.let { cancellation ->
                        call.respond(
                            message = cancellation.toDto(),
                            status = HttpStatusCode.OK
                        )
                    }
            } catch (e: Exception) {
                app.log.info("GETTING CANCELLATION RECEIVED BY ID ERROR: ${e.message}")
                call.respond("GETTING CANCELLATION RECEIVED BY ID ERROR: ${e.message}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}