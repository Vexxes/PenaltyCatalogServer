package de.vexxes.domain.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class PenaltyCategory(
    @BsonId
    val _id: String = ObjectId().toString(),
    val name: String = ""
)