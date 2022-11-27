package de.vexxes.domain.model

sealed class Endpoint(val path: String) {
    object Root: Endpoint(path = "/")
    object GetAllPlayers: Endpoint(path = "/get_players")
    object GetPlayerById: Endpoint(path = "/get_player/{playerId}")
    object UpdatePlayer: Endpoint(path = "/update_player")
    object DeletePlayer: Endpoint(path = "/delete_player/{playerId}")
    object GetPlayersBySearch: Endpoint(path = "/get_players_by_search")
    object Unauthorized: Endpoint(path = "/unauthorized")
}