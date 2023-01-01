package de.vexxes.domain.model

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class PenaltyType(
    val _id: String = ObjectId().toString(),
    val name: String = "",
    val categoryID: String = "",
    val description: String = "",
    val isBeer: Boolean = false,
    val value: String = ""
)