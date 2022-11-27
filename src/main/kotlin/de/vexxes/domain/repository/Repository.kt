package de.vexxes.domain.repository

import de.vexxes.domain.model.ApiResponse
import de.vexxes.domain.model.Player

interface Repository {
    suspend fun getAllPlayers(sortOrder: Int = 1): ApiResponse
    suspend fun getPlayerById(playerId: String?): ApiResponse
    suspend fun updatePlayer(player: Player): Boolean
    suspend fun deletePlayer(playerId: String?): Boolean
    suspend fun getPlayersBySearch(searchText: String): ApiResponse
}