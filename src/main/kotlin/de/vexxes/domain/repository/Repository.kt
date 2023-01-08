package de.vexxes.domain.repository

import de.vexxes.domain.model.ApiResponse
import de.vexxes.domain.model.PenaltyReceived
import de.vexxes.domain.model.PenaltyType
import de.vexxes.domain.model.Player
import org.litote.kmongo.Id

interface Repository {
    suspend fun getAllPlayers(): List<Player>
    suspend fun postPlayer(player: Player): Id<Player>?
    suspend fun getPlayerById(id: String): Player?
    suspend fun getPlayersBySearch(name: String): List<Player>
    suspend fun updatePlayer(id: String, player: Player): Boolean
    suspend fun deletePlayer(id: String): Boolean


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