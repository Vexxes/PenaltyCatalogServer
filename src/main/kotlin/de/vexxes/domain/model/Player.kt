package de.vexxes.domain.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Player(
    @BsonId
    val _id: String = ObjectId().toString(),
    val number: Int,
    val firstName: String,
    val lastName: String,
    val zipcode: Int,
    val city: String,
    val playedGames: Int,
    val goals: Int,
    val yellowCards: Int,
    val twoMinutes: Int,
    val redCards: Int
)