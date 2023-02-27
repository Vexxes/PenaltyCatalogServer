package de.vexxes.domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id

@Serializable
data class Event(
    @Contextual
    @SerialName("_id")
    val id: Id<Event>? = null,
    val title: String,
    val startOfEvent: LocalDateTime,
    val startOfMeeting: LocalDateTime,
    val address: String = "",
    val description: String = "",
    val players: List<EventPlayerAvailability>,
    val type: EventType
)

@Serializable
data class EventPlayerAvailability(
    @Contextual
    val playerId: Id<Player>,
    val playerState: PlayerState = PlayerState.UNDEFINED
)

enum class PlayerState {
    UNDEFINED, PRESENT, CANCELED, PAID_BEER, NOT_PRESENT
}

enum class EventType {
    TRAINING, GAME, TESTGAME, MISCELLANEOUS
}
