package de.vexxes.data.repository

import de.vexxes.domain.model.ApiResponse
import de.vexxes.domain.model.Player
import de.vexxes.domain.repository.Repository
import org.litote.kmongo.ascending
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.upsert

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

    override suspend fun getPlayerById(playerId: String?): ApiResponse {
        return ApiResponse(
            success = true,
            player = players.find(filter = Player::_id eq playerId).toList()
        )
    }

    override suspend fun updatePlayer(player: Player): Boolean {
        return players.updateOneById(
            id = player._id,
            update = player,
            options = upsert()
        ).wasAcknowledged()
    }

    override suspend fun deletePlayer(playerId: String?): Boolean {
        return players.deleteOneById(
            id = playerId!!
        ).wasAcknowledged()
    }
}