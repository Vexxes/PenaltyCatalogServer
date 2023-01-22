package de.vexxes.domain.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

@Serializable
data class PenaltyCategory(
    @BsonId
    val _id: Id<PenaltyCategory>,
    val name: String = ""
)