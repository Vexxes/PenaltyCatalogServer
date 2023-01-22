package de.vexxes.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id

@Serializable
data class PenaltyType(
    @Contextual
    @SerialName("_id")
    val id: Id<PenaltyType>? = null,
    val name: String,
    val description: String,
    val isBeer: Boolean,
    val value: Double
)