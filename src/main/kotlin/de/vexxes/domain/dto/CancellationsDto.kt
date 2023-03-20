package de.vexxes.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class CancellationsDto(
    val id: String? = null,
    val playerId: String,
    val eventId: String,
    val timeOfCancellation: String
)