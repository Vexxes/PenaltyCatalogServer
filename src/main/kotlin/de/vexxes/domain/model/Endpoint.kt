package de.vexxes.domain.model

sealed class Endpoint(val path: String) {
    object Root: Endpoint(path = "/")

    object GetAllPlayers: Endpoint(path = "/get_players")
    object GetPlayerById: Endpoint(path = "/get_player/{playerId}")
    object GetPlayersBySearch: Endpoint(path = "/get_players_by_search")
    object UpdatePlayer: Endpoint(path = "/update_player")
    object DeletePlayer: Endpoint(path = "/delete_player/{playerId}")

    object GetAllCategories: Endpoint(path = "/get_categories")
    object GetAllPenalties: Endpoint(path = "/get_penalties")
    object GetPenaltyById: Endpoint(path = "/get_penalty/{penaltyId}")
    object GetPenaltiesBySearch: Endpoint(path = "/get_penalty_by_search")
    object UpdatePenalty: Endpoint(path = "/update_penalty")
    object DeletePenalty: Endpoint(path = "/delete_penalty/{penaltyId}")

    object Unauthorized: Endpoint(path = "/unauthorized")
}