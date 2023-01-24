package de.vexxes.domain.extension

import de.vexxes.domain.dto.EventDto
import de.vexxes.domain.model.Event
import kotlinx.datetime.LocalDateTime

fun Event.toDto(): EventDto =
    EventDto(
        id = this.id.toString(),
        title = this.title,
        startOfEvent = this.startOfEvent.toString(),
        startOfMeeting = this.startOfMeeting.toString(),
        address = this.address,
        description = this.description
    )

fun EventDto.toEvent(): Event =
    Event(
        title= this.title,
        startOfEvent = LocalDateTime.parse(this.startOfEvent),
        startOfMeeting = LocalDateTime.parse(this.startOfMeeting),
        address = this.address,
        description = this.description
    )