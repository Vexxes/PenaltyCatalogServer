package de.vexxes.domain.repository

import de.vexxes.domain.model.ApiResponse
import de.vexxes.domain.model.PenaltyType
import org.litote.kmongo.Id

interface Repository {
    suspend fun getDeclaredPenalties(penaltyId: Id<PenaltyType>?): ApiResponse
}