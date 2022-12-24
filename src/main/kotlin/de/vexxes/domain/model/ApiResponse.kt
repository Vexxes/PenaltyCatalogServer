package de.vexxes.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val success: Boolean,
    val penalty: List<Penalty>? = null,
    val penaltyCategory: List<PenaltyCategory>? = null,
    val player: List<Player>? = null,
    val penaltyHistory: List<PenaltyHistory>? = null,
    val cancellation: List<Cancellation>? = null,
    val event: List<Event>? = null,
    val message: String? = null,
)