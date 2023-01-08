package de.vexxes.domain.extension

import de.vexxes.domain.dto.PenaltyTypeDto
import de.vexxes.domain.model.PenaltyType

fun PenaltyType.toDto(): PenaltyTypeDto =
    PenaltyTypeDto(
        id = this.id.toString(),
        name = this.name,
        description = this.description,
        isBeer = this.isBeer,
        value = this.value
    )

fun PenaltyTypeDto.toPenaltyType(): PenaltyType =
    PenaltyType(
        name = this.name,
        description = this.description,
        isBeer = this.isBeer,
        value = this.value
    )