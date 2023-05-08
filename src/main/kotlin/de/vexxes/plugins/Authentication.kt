package de.vexxes.plugins

import de.vexxes.util.Constants.keycloakAddress
import de.vexxes.util.Constants.keycloakOAUth
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.sessions.*


fun Application.configureAuth() {
    install(Authentication) {
        oauth(keycloakOAUth) {
            client = HttpClient(Apache)
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "keycloak",
                    authorizeUrl = "$keycloakAddress/realms/PenaltyCatalog/protocol/openid-connect/auth",
                    accessTokenUrl = "$keycloakAddress/realms/PenaltyCatalog/protocol/openid-connect/token",
                    clientId = "KtorApp",
                    clientSecret = "123456",
                    accessTokenRequiresBasicAuth = false,
                    requestMethod = HttpMethod.Post,
                    defaultScopes = listOf("openid", "roles")
                )
            }
            urlProvider = { "http://localhost:8088/login" }
        }
    }

    install(Sessions) {
        cookie<UserSession>("user_session")
    }
}