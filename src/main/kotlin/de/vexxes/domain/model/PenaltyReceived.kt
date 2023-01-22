package de.vexxes.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id

@Serializable
data class PenaltyReceived(
    @Contextual
    @SerialName("_id")
    val id: Id<PenaltyReceived>? = null,
    @Contextual
    val penaltyTypeId: Id<PenaltyType>,
    @Contextual
    val playerId: Id<Player>,
    val timeOfPenalty: LocalDate,
    val timeOfPenaltyPaid: LocalDate? = null
)