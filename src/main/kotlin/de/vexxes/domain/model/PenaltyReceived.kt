package de.vexxes.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

@Serializable
data class PenaltyReceived(
    @BsonId
    val _id: Id<PenaltyReceived>,
    val penaltyTypeId: Id<PenaltyType>,
    val playerId: Id<Player>,
    val timeOfPenalty: LocalDate,
    val timeOfPenaltyPaid: LocalDate?
)