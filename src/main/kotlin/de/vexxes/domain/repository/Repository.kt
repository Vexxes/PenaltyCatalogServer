package de.vexxes.domain.repository

import de.vexxes.domain.model.ApiResponse
import de.vexxes.domain.model.PenaltyReceived
import de.vexxes.domain.model.PenaltyType
import de.vexxes.domain.model.Player
import org.litote.kmongo.Id

interface Repository {
    suspend fun getAllPlayers(sortOrder: Int = 1): ApiResponse
    suspend fun getPlayerById(playerId: Id<Player>?): ApiResponse
    suspend fun getPlayersBySearch(searchText: String): ApiResponse
    suspend fun updatePlayer(player: Player): Boolean
    suspend fun deletePlayer(playerId: Id<Player>?): Boolean

    suspend fun getAllCategories(): ApiResponse
    suspend fun getAllPenalties(): ApiResponse
    suspend fun getPenaltyById(penaltyId: Id<PenaltyType>?): ApiResponse
    suspend fun getDeclaredPenalties(penaltyId: Id<PenaltyType>?): ApiResponse
    suspend fun getPenaltiesBySearch(searchText: String): ApiResponse
    suspend fun updatePenalty(penaltyType: PenaltyType): Boolean
    suspend fun deletePenalty(penaltyId: Id<PenaltyType>?): Boolean

    suspend fun getAllPenaltyHistory(): ApiResponse
    suspend fun getPenaltyHistoryById(penaltyHistoryId: Id<PenaltyReceived>?): ApiResponse
    suspend fun getPenaltyHistoryBySearch(searchText: String): ApiResponse
    suspend fun updatePenaltyHistory(penaltyReceived: PenaltyReceived): Boolean
    suspend fun deletePenaltyHistory(penaltyHistoryId: Id<PenaltyReceived>?): Boolean
}