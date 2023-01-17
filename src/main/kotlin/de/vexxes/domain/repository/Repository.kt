package de.vexxes.domain.repository

import de.vexxes.domain.model.ApiResponse
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

    suspend fun getDeclaredPenalties(penaltyId: Id<PenaltyType>?): ApiResponse

}