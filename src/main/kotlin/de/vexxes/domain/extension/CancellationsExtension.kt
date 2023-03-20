package de.vexxes.domain.extension

import de.vexxes.domain.dto.CancellationsDto
import de.vexxes.domain.model.Cancellation
import kotlinx.datetime.LocalDateTime
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId

fun Cancellation.toDto(): CancellationsDto =
    CancellationsDto(
        id = this.id.toString(),
        playerId = this.playerId.toString(),
        eventId = this.eventId.toString(),
        timeOfCancellation = this.timeOfCancellation.toString()
    )

fun CancellationsDto.toCancellation(): Cancellation =
    Cancellation(
        playerId = ObjectId(this.playerId).toId(),
        eventId = ObjectId(this.eventId).toId(),
        timeOfCancellation = LocalDateTime.parse(this.timeOfCancellation)
    )