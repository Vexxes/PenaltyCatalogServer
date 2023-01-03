package de.vexxes.domain.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

@Serializable
data class Player(
    @BsonId
    val _id: Id<Player>,
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