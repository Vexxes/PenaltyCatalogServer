package de.vexxes.plugins

import de.vexxes.keycloakOAUth
import de.vexxes.keycloakProvider
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.sessions.*


fun Application.configureAuth() {
    install(Authentication) {
        oauth(keycloakOAUth) {
            client = HttpClient(Apache)
            providerLookup = { keycloakProvider }
            urlProvider = {
                //TODO ist das hier richtig?
                "http://localhost:8088/callback"
            }
        }
    }

    install(Sessions) {
        cookie<UserSession>("user_session")
    }
}