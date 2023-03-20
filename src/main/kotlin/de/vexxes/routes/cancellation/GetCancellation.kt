package de.vexxes.routes.cancellation

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.extension.toDto
import de.vexxes.domain.model.Cancellation
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.CancellationRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllCancellation(
    app: Application,
    repository: CancellationRepository,
    validateBearerToken: ValidateBearerToken
) {
    get(Endpoint.GetAllCancellation.path) {
        if (validateBearerToken.validateAll(call.request.headers["Authorization"].toString())) {
            try {
                call.respond(
                    message = repository.getAllCancellation().map(Cancellation::toDto),
                    status = HttpStatusCode.OK
                )
            } catch (e: Exception) {
                app.log.info("GETTING CANCELLATION RECEIVED ERROR: ${e.message}")
                call.respond("GETTING CANCELLATION RECEIVED ERROR: ${e.message}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}