package de.vexxes.domain.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Player(
    @BsonId
    val _id: String = ObjectId().toString(),
    val number: Int = 0,
    val firstName: String = "",
    val lastName: String = "",
    val birthday: String = "",
    val street: String = "",
    val zipcode: Int = 0,
    val city: String = "",
    val playedGames: Int = 0,
    val goals: Int = 0,
    val yellowCards: Int = 0,
    val twoMinutes: Int = 0,
    val redCards: Int = 0
)