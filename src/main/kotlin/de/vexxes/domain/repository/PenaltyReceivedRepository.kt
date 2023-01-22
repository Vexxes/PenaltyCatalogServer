package de.vexxes.domain.repository

import de.vexxes.domain.model.PenaltyReceived
import org.litote.kmongo.Id

interface PenaltyReceivedRepository {
    suspend fun getAllPenaltyReceived(): List<PenaltyReceived>
    suspend fun getPenaltyReceivedById(id: String): PenaltyReceived?
    suspend fun getPenaltyReceivedForPlayer(playerId: String): List<PenaltyReceived>
    suspend fun postPenaltyReceived(penaltyReceived: PenaltyReceived): Id<PenaltyReceived>?
    suspend fun updatePenaltyReceived(id: String, penaltyReceived: PenaltyReceived): Boolean
    suspend fun deletePenaltyReceived(id: String): Boolean

}