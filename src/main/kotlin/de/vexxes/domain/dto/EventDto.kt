package de.vexxes.domain.dto

import de.vexxes.domain.model.EventType
import de.vexxes.domain.model.PlayerState
import kotlinx.serialization.Serializable

@Serializable
data class EventDto(
    val id: String? = null,
    val title: String,
    val startOfEvent: String = "",
    val startOfMeeting: String = "",
    val address: String = "",
    val description: String = "",
    val players: List<PlayerStateDto>,
    val type: EventType
)

@Serializable
data class PlayerStateDto(
    val playerId: String,
    val playerState: PlayerState = PlayerState.UNDEFINED
)