package de.vexxes.domain.extension

import de.vexxes.domain.dto.PenaltyReceivedDto
import de.vexxes.domain.model.PenaltyReceived
import kotlinx.datetime.LocalDate
import org.litote.kmongo.toId

fun PenaltyReceived.toDto(): PenaltyReceivedDto =
    PenaltyReceivedDto(
        id = this.id.toString(),
        penaltyTypeId = this.penaltyTypeId.toString(),
        playerId = this.playerId.toString(),
        timeOfPenalty = this.timeOfPenalty.toString(),
        timeOfPenaltyPaid = this.timeOfPenaltyPaid.toString()
    )

fun PenaltyReceivedDto.toPenaltyReceived(): PenaltyReceived =
    PenaltyReceived(
        penaltyTypeId = this.penaltyTypeId.toId(),
        playerId = this.playerId.toId(),
        timeOfPenalty = LocalDate.parse(this.timeOfPenalty),
        timeOfPenaltyPaid = this.timeOfPenaltyPaid?.let { LocalDate.parse(it) }
    )
