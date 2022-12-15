package de.vexxes.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val success: Boolean,
    val penalty: List<Penalty>? = null,
    val penaltyCategory: List<PenaltyCategory>? = null,
    val player: List<Player>? = null,
    val penaltyHistory: PenaltyHistory? = null,
    val cancellation: Cancellation? = null,
    val event: Event? = null,
    val message: String? = null,
)