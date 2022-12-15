package de.vexxes.domain.model

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class Penalty(
    val _id: String = ObjectId().toString(),
    val name: String = "",
    val nameOfCategory: String = "",
    val description: String = "",
    val isBeer: Boolean = false,
    val value: Int = 0
)