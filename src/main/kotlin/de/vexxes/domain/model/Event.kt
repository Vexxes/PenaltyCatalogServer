package de.vexxes.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

@Serializable
data class Event(
    @BsonId
    val _id: Id<Event>,
    val title: String,
    val startOfEvent: LocalDate,
    val startOfMeeting: LocalDate,
    val address: String,
    val description: String
)