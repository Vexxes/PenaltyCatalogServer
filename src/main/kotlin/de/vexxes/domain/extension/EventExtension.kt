package de.vexxes.domain.extension

import de.vexxes.domain.dto.EventDto
import de.vexxes.domain.dto.PlayerStateDto
import de.vexxes.domain.model.Event
import de.vexxes.domain.model.PlayerState
import kotlinx.datetime.LocalDateTime
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId

fun Event.toDto(): EventDto =
    EventDto(
        id = this.id.toString(),
        title = this.title,
        startOfEvent = this.startOfEvent.toString(),
        startOfMeeting = this.startOfMeeting.toString(),
        address = this.address,
        description = this.description,
        players = toDtoList(this.players)
    )

fun EventDto.toEvent(): Event =
    Event(
        title = this.title,
        startOfEvent = LocalDateTime.parse(this.startOfEvent),
        startOfMeeting = LocalDateTime.parse(this.startOfMeeting),
        address = this.address,
        description = this.description,
        players = toPlayerState(this.players)
    )

fun PlayerState.toDto(): PlayerStateDto =
    PlayerStateDto(
        playerId = this.playerId.toString(),
        state = this.state
    )

fun PlayerStateDto.toPlayerState(): PlayerState =
    PlayerState(
        playerId = ObjectId(this.playerId).toId(),
        state = this.state
    )

private fun toDtoList(players: List<PlayerState>): List<PlayerStateDto> {
    val playerList: MutableList<PlayerStateDto> = emptyArray<PlayerStateDto>().toMutableList()
    players.forEach { a -> playerList.add(a.toDto()) }
    return playerList
}

private fun toPlayerState(players: List<PlayerStateDto>): List<PlayerState> {
    val playerList: MutableList<PlayerState> = emptyArray<PlayerState>().toMutableList()
    players.forEach { a -> playerList.add(a.toPlayerState()) }
    return playerList
}
