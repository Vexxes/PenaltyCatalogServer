package de.vexxes.domain.model

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class PenaltyHistory(
    val _id: String = ObjectId().toString(),
    val penaltyName: String = "",
    val playerName: String = "",
    val penaltyValue: String = "",
    val penaltyIsBeer: Boolean = false,
    val timeOfPenalty: String = "",
    val penaltyPaid: Boolean = false
)