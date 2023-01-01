package de.vexxes.domain.repository

import de.vexxes.domain.model.ApiResponse
import de.vexxes.domain.model.PenaltyType
import de.vexxes.domain.model.PenaltyReceived
import de.vexxes.domain.model.Player

interface Repository {
    suspend fun getAllPlayers(sortOrder: Int = 1): ApiResponse
    suspend fun getPlayerById(playerId: String?): ApiResponse
    suspend fun getPlayersBySearch(searchText: String): ApiResponse
    suspend fun updatePlayer(player: Player): Boolean
    suspend fun deletePlayer(playerId: String?): Boolean

    suspend fun getAllCategories(): ApiResponse
    suspend fun getAllPenalties(): ApiResponse
    suspend fun getPenaltyById(penaltyId: String?): ApiResponse
    suspend fun getDeclaredPenalties(penaltyName: String): ApiResponse
    suspend fun getPenaltiesBySearch(searchText: String): ApiResponse
    suspend fun updatePenalty(penaltyType: PenaltyType): Boolean
    suspend fun deletePenalty(penaltyId: String?): Boolean

    suspend fun getAllPenaltyHistory(): ApiResponse
    suspend fun getPenaltyHistoryById(penaltyHistoryId: String?): ApiResponse
    suspend fun getPenaltyHistoryBySearch(searchText: String): ApiResponse
    suspend fun updatePenaltyHistory(penaltyReceived: PenaltyReceived): Boolean
    suspend fun deletePenaltyHistory(penaltyHistoryId: String?): Boolean
}