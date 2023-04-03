package de.vexxes

import io.ktor.http.*
import io.ktor.server.auth.*

val keycloakAddress = "http://localhost:8180"

val keycloakProvider = OAuthServerSettings.OAuth2ServerSettings(
    name = "keycloak",
    authorizeUrl = "$keycloakAddress/realms/ktor/protocol/openid-connect/auth",
    accessTokenUrl = "$keycloakAddress/realms/ktor/protocol/openid-connect/token",
    clientId = "KtorApp",
    clientSecret = "123456",
    accessTokenRequiresBasicAuth = false,
    requestMethod = HttpMethod.Post,
    defaultScopes = listOf("openid","roles")
)
val keycloakOAUth = "keycloakOAuth"