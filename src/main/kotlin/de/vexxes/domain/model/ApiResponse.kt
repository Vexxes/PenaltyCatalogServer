package de.vexxes.domain.model

import de.vexxes.domain.dto.PlayerDto
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val success: Boolean,
    val penaltyType: List<PenaltyType>? = null,
    val penaltyCategory: List<PenaltyCategory>? = null,
    val player: List<PlayerDto>? = null,
    val penaltyReceived: List<PenaltyReceived>? = null,
    val cancellation: List<Cancellation>? = null,
    val event: List<Event>? = null,
    val message: String? = null,
)