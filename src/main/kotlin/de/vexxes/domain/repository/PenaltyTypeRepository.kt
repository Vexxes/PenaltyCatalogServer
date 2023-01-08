package de.vexxes.domain.repository

import de.vexxes.domain.model.PenaltyType
import org.litote.kmongo.Id

interface PenaltyTypeRepository {
    suspend fun getAllPenaltyTypes(): List<PenaltyType>
    suspend fun getPenaltyTypeById(id: String): PenaltyType?
    suspend fun postPenaltyType(penaltyType: PenaltyType): Id<PenaltyType>?
    suspend fun updatePenaltyType(id: String, penaltyType: PenaltyType): Boolean
    suspend fun deletePenaltyType(id: String): Boolean
    suspend fun getPenaltyTypeBySearch(name: String): List<PenaltyType>
}