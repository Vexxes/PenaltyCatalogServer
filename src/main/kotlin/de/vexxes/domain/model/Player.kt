package de.vexxes.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id

@Serializable
data class Player(
    @Contextual
    @SerialName("_id")
    val id: Id<Player>? = null,
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