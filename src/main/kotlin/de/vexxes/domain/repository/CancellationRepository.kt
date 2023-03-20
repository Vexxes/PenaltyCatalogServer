package de.vexxes.domain.repository

import de.vexxes.domain.model.Cancellation
import org.litote.kmongo.Id

interface CancellationRepository {
    suspend fun getAllCancellation(): List<Cancellation>
    suspend fun getCancellationById(id: String): Cancellation?
    suspend fun getCancellationForEvent(eventId: String): List<Cancellation>
    suspend fun getCancellationForPlayer(playerId: String): List<Cancellation>
    suspend fun postCancellation(cancellation: Cancellation): Id<Cancellation>?
    suspend fun updateCancellation(id: String, request: Cancellation): Boolean
    suspend fun deleteCancellation(id: String): Boolean
}