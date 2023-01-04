package de.vexxes.domain.extension

import de.vexxes.domain.dto.PlayerDto
import de.vexxes.domain.model.Player

fun Player.toDto(): PlayerDto =
    PlayerDto(
        id = this.id.toString(),
        number = this.number,
        firstName = this.firstName,
        lastName = this.lastName,
        birthday = this.birthday,
        street = this.street,
        zipcode = this.zipcode,
        city = this.city,
        playedGames = this.playedGames,
        goals = this.goals,
        yellowCards = this.yellowCards,
        twoMinutes = this.twoMinutes,
        redCards = this.redCards
    )

fun PlayerDto.toPlayer(): Player =
    Player(
        number = this.number,
        firstName = this.firstName,
        lastName = this.lastName,
        birthday = this.birthday,
        street = this.street,
        zipcode = this.zipcode,
        city = this.city,
        playedGames = this.playedGames,
        goals = this.goals,
        yellowCards = this.yellowCards,
        twoMinutes = this.twoMinutes,
        redCards = this.redCards
    )