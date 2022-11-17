package de.vexxes.data.repository

import de.vexxes.domain.model.ApiResponse
import de.vexxes.domain.model.Player
import de.vexxes.domain.repository.Repository
import org.litote.kmongo.ascending
import org.litote.kmongo.coroutine.CoroutineDatabase

class RepositoryImpl(
    database: CoroutineDatabase
): Repository {

    private val players = database.getCollection<Player>()

    override suspend fun getAllPlayers(): ApiResponse {
        return ApiResponse(
            success = true,
            player = players.find().sort(ascending(Player::number)).toList()
        )
    }
}