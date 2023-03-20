package de.vexxes.domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id

@Serializable
data class Cancellation(
    @Contextual
    @SerialName("_id")
    val id: Id<Cancellation>? = null,
    @Contextual
    val playerId: Id<Player>,
    @Contextual
    val eventId: Id<Event>,
    val timeOfCancellation: LocalDateTime
)