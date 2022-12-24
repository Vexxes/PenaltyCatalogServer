package de.vexxes.domain.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class PenaltyHistory(
    val _id: String = ObjectId().toString(),
    val penaltyName: String = "",
    val playerName: String = "",
    val penaltyValue: Int = 0,
    val penaltyIsBeer: Boolean = false,
    val timeOfPenalty: Instant = Clock.System.now(),
    val penaltyPaid: Boolean = false
)