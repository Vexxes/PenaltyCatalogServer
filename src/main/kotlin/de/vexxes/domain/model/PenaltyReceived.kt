package de.vexxes.domain.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class PenaltyReceived(
    val _id: String = ObjectId().toString(),
    val penaltyTypeId: String = "",
    val playerId: String = "",
    val timeOfPenalty: Instant = Clock.System.now(),
    val timeOfPenaltyPaid: Instant? = null
)