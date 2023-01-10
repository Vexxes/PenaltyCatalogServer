package de.vexxes.domain.model

sealed class Endpoint(val path: String) {
    object Root : Endpoint(path = "/")

    object GetAllPlayers : Endpoint(path = "/players")
    object PostPlayer : Endpoint(path = "/player")
    object GetPlayerById : Endpoint(path = "/player/{playerId}")
    object GetPlayersBySearch : Endpoint(path = "/player-search")
    object UpdatePlayer : Endpoint(path = "/player/{playerId}")
    object DeletePlayer : Endpoint(path = "/player/{playerId}")

    object GetAllPenaltyTypes : Endpoint(path = "/penalty-types")
    object GetPenaltyTypeById : Endpoint(path = "/penalty-type/{penaltyTypeId}")
    object GetDeclaredPenalties : Endpoint(path = "/get_penalty_declared")
    object GetPenaltyTypesBySearch : Endpoint(path = "/penalty-type-search")
    object PostPenaltyType : Endpoint(path = "/penalty-type")
    object UpdatePenaltyType : Endpoint(path = "/penalty-type/{penaltyTypeId}")
    object DeletePenaltyType : Endpoint(path = "/penalty-type/{penaltyTypeId}")

    object GetAllPenaltyHistory : Endpoint(path = "/get_penalty_history")
    object GetPenaltyHistoryById : Endpoint(path = "/get_penalty_history/{penaltyHistoryId}")
    object GetPenaltyHistoryBySearch : Endpoint(path = "/get_penalty_history_by_search")
    object UpdatePenaltyHistory : Endpoint(path = "/update_penaltyHistory")
    object DeletePenaltyHistory : Endpoint(path = "/delete_penalty_history/{penaltyHistoryId}")

    object Unauthorized : Endpoint(path = "/unauthorized")
}