package de.vexxes.routes.cancellation

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.CancellationRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteCancellation(
    app: Application,
    repository: CancellationRepository,
    validateBearerToken: ValidateBearerToken
) {
    delete(Endpoint.DeleteCancellation.path) {
        if (validateBearerToken.validateAdmin(call.request.headers["Authorization"].toString())) {
            try {
                val id = call.parameters["cancellationId"].toString()
                val deleteSuccessfully = repository.deleteCancellation(id)
                if (deleteSuccessfully) {
                    call.respond(
                        message = true,
                        status = HttpStatusCode.OK
                    )
                } else {
                    call.respond(
                        message = false,
                        status = HttpStatusCode.NotFound
                    )
                }

            } catch (e: Exception) {
                app.log.info("DELETE CANCELLATION INFO ERROR: $e")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}