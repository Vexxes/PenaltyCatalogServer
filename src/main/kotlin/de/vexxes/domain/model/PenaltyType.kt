package de.vexxes.domain.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

@Serializable
data class PenaltyType(
    @BsonId
    val _id: Id<PenaltyType>,
    val name: String,
    val categoryID: Id<PenaltyCategory>,
    val description: String,
    val isBeer: Boolean,
    val value: String,
    val index: Int
)