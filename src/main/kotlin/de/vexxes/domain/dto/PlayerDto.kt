package de.vexxes.domain.dto

import kotlinx.serialization.Serializable


@Serializable
data class PlayerDto(
    val id: String? = null,
    val number: Int,
    val firstName: String,
    val lastName: String,
    val birthday: String,
    val street: String,
    val zipcode: Int,
    val city: String,
    val playedGames: Int,
    val goals: Int,
    val yellowCards: Int,
    val twoMinutes: Int,
    val redCards: Int
)