package de.vexxes.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Cancellation(
    val id: Int,
    val player: Player,
    val timeOfCancellation: String,
    val cancelFor: String
)