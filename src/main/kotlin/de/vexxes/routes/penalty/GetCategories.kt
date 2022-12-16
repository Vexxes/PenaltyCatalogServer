package de.vexxes.routes.penalty

import de.vexxes.authorization.ValidateBearerToken
import de.vexxes.domain.model.Endpoint
import de.vexxes.domain.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllCategories(
    app: Application,
    repository: Repository,
    validateBearerToken: ValidateBearerToken
) {
    get(Endpoint.GetAllCategories.path) {
        if (validateBearerToken.validateAll(call.request.headers["Authorization"].toString())) {
            try {
                call.respond(
                    message = repository.getAllCategories(),
                    status = HttpStatusCode.OK
                )
            } catch (e: Exception) {
                app.log.info("GETTING CATEGORIES ERROR: ${e.message}")
                call.respond("GETTING CATEGORIES ERROR: ${e.message}")
            }
        } else {
            app.log.info("authentication failed")
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}