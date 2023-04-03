package de.vexxes.plugins

import com.auth0.jwt.JWT
import de.vexxes.keycloakOAUth
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Application.configureSecurity() {

    routing {
        authenticate(keycloakOAUth) {
            get("/login") {
                call.respondRedirect("/callback")
            }

            get("/callback") {
                val principal: OAuthAccessTokenResponse.OAuth2? = call.authentication.principal()
                call.sessions.set(
                    UserSession(
                        JWT.decode(principal?.accessToken).getClaim("realm_access").asMap()["roles"]
                    )
                )
                call.respondRedirect("/events")
            }
        }
    }
}

data class UserSession(val name: Any?) : Principal
