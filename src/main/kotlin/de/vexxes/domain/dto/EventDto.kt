package de.vexxes.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class EventDto(
    val id: String? = null,
    val title: String,
    val startOfEvent: String = "",
    val startOfMeeting: String = "",
    val address: String = "",
    val description: String = "",
    val players: List<PlayerStateDto> = emptyList(),
    val type: String
)

@Serializable
data class PlayerStateDto(
    val playerId: String,
    val playerState: String
)