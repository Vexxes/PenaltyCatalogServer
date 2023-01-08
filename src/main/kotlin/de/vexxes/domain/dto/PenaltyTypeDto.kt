package de.vexxes.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class PenaltyTypeDto(
    val id: String? = null,
    val name: String,
    val description: String,
    val isBeer: Boolean,
    val value: Double
)