package de.vexxes.routes.cancellation

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.dto.CancellationsDto
import de.vexxes.domain.extension.toCancellation
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.CancellationRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updateCancellation(
    app: Application,
    repository: CancellationRepository,
    validateBearerToken: ValidateBearerToken
) {
    put(Endpoint.UpdateCancellation.path) {
        if (validateBearerToken.validateAdmin(call.request.headers["Authorization"].toString())) {
            try {
                val id = call.parameters["cancellationId"].toString()
                val cancellationRequest = call.receive<CancellationsDto>()
                val cancellation = cancellationRequest.toCancellation()

                val updatedSuccessfully = repository.updateCancellation(id, cancellation)
                if (updatedSuccessfully) {
                    call.respond(
                        message = true,
                        status = HttpStatusCode.OK
                    )
                } else {
                    call.respond(
                        message = false,
                        status = HttpStatusCode.BadRequest
                    )
                }
            } catch (e: Exception) {
                app.log.info("UPDATE CANCELLATION INFO ERROR: ${e.message} ${e.cause}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}