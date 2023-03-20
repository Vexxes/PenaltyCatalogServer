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

fun Route.postCancellation(
    app: Application,
    repository: CancellationRepository,
    validateBearerToken: ValidateBearerToken
) {
    post(Endpoint.PostCancellation.path) {
        if (validateBearerToken.validateAll(call.request.headers["Authorization"].toString())) {
            try {
                val request = call.receive<CancellationsDto>()
                val cancellation = request.toCancellation()

                repository.postCancellation(cancellation)
                    ?.let { cancellationId ->
                        call.respond(
                            message = cancellationId.toString(),
                            status = HttpStatusCode.Created
                        )
                    } ?: call.respond(HttpStatusCode.BadRequest, "Bad request")
            } catch (e: Exception) {
                app.log.info("POST CANCELLATION ERROR: ${e.message}")
                call.respond("POST CANCELLATION ERROR: ${e.message}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}