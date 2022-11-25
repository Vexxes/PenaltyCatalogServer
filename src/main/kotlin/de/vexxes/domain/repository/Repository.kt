package de.vexxes.domain.repository

import de.vexxes.domain.model.ApiResponse
import de.vexxes.domain.model.Player

interface Repository {
    suspend fun getAllPlayers(): ApiResponse
    suspend fun getPlayerById(playerId: String?): ApiResponse
    suspend fun updatePlayer(player: Player): Boolean
    suspend fun deletePlayer(playerId: String?): Boolean
}