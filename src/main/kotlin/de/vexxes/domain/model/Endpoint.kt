package de.vexxes.domain.model

sealed class Endpoint(val path: String) {
    object Root: Endpoint(path = "/")
    object GetAllPlayers: Endpoint(path = "/get_players")
    object Unauthorized: Endpoint(path = "/unauthorized")
}