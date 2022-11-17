package de.vexxes.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureAuth() {
    install(Authentication) {
//        session<UserSession>(name = "auth_session") {
//            validate { session ->
//                session
//            }
//            challenge {
//                call.respondRedirect(Endpoint.Unauthorized.path)
//            }
//        }
    }
}