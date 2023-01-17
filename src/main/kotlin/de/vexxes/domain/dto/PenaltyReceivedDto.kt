package de.vexxes.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class PenaltyReceivedDto(
    val id: String? = null,
    val penaltyTypeId: String,
    val playerId: String,
    val timeOfPenalty: String,
    val timeOfPenaltyPaid: String?
)