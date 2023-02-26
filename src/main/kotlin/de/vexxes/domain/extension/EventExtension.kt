package de.vexxes.domain.extension

import de.vexxes.domain.dto.EventDto
import de.vexxes.domain.dto.PlayerStateDto
import de.vexxes.domain.model.Event
import de.vexxes.domain.model.PlayerState
import de.vexxes.domain.model.State
import de.vexxes.domain.model.Type
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
        players = toDtoList(this.players),
        type = this.type.toString()
    )

fun EventDto.toEvent(): Event =
    Event(
        title = this.title,
        startOfEvent = LocalDateTime.parse(this.startOfEvent),
        startOfMeeting = LocalDateTime.parse(this.startOfMeeting),
        address = this.address,
        description = this.description,
        players = toPlayerState(this.players),
        type = Type.valueOf(this.type)
    )

fun PlayerState.toDto(): PlayerStateDto =
    PlayerStateDto(
        playerId = this.playerId.toString(),
        playerState = this.playerState.toString()
    )

fun PlayerStateDto.toPlayerState(): PlayerState =
    PlayerState(
        playerId = ObjectId(this.playerId).toId(),
        playerState = State.valueOf(this.playerState)
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