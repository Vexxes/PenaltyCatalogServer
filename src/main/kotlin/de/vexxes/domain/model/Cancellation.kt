package de.vexxes.domain.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

@Serializable
data class Cancellation(
    @BsonId
    val _id: Id<Cancellation>,
    val playerId: Id<Player>,
    val eventId: Id<Event>,
    val cancelFor: String
)