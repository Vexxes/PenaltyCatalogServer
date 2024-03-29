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
    object GetPenaltyTypesBySearch : Endpoint(path = "/penalty-type-search")
    object PostPenaltyType : Endpoint(path = "/penalty-type")
    object UpdatePenaltyType : Endpoint(path = "/penalty-type/{penaltyTypeId}")
    object DeletePenaltyType : Endpoint(path = "/penalty-type/{penaltyTypeId}")

    object GetAllPenaltyReceived : Endpoint(path = "/penalties-received")
    object GetPenaltyReceivedById : Endpoint(path = "/penalty-received/{penaltyReceivedId}")
    object PostPenaltyReceived : Endpoint(path = "/penalty-received")
    object GetPenaltyReceivedByPlayerId : Endpoint(path = "/penalty-received-playerId/{playerId}")
    object UpdatePenaltyReceived : Endpoint(path = "/penalty-received/{penaltyReceivedId}")
    object DeletePenaltyReceived : Endpoint(path = "/penalty-received/{penaltyReceivedId}")

    object GetAllEvents : Endpoint(path = "/events")
    object GetEventById : Endpoint(path = "/event/{eventId}")
    object PostEvent : Endpoint(path = "/event")
    object UpdateEvent : Endpoint(path = "/event/{eventId}")
    object DeleteEvent : Endpoint(path = "/event/{eventId}")
    object PlayerEvent : Endpoint(path = "/event-player/{eventId}")

    object GetAllCancellation : Endpoint(path = "/cancellation")
    object GetCancellationById : Endpoint(path = "/cancellation/{cancellationId}")
    object GetCancellationByEventId : Endpoint(path = "/cancellation/event/{eventId}")
    object GetCancellationByPlayerId : Endpoint(path = "/cancellation/player/{playerId}")
    object PostCancellation : Endpoint(path = "/cancellation")
    object UpdateCancellation : Endpoint(path = "/cancellation/{cancellationId}")
    object DeleteCancellation : Endpoint(path = "/cancellation/{cancellationId}")

    object Unauthorized : Endpoint(path = "/unauthorized")
}