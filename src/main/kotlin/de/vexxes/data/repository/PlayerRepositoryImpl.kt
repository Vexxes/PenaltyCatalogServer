package de.vexxes.data.repository

import de.vexxes.domain.model.Player
import de.vexxes.domain.repository.PlayerRepository
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId
import org.litote.kmongo.or
import org.litote.kmongo.regex

class PlayerRepositoryImpl(
    database: CoroutineDatabase
): PlayerRepository {
    private val players = database.getCollection<Player>()

    override suspend fun getAllPlayers(): List<Player> =
        players.find().toList()

    override suspend fun getPlayerById(id: String): Player? {
        val bsonId: Id<Player> = ObjectId(id).toId()
        return players.findOne(Player::id eq bsonId)
    }

    override suspend fun postPlayer(player: Player): Id<Player>? {
        players.insertOne(player)
        return player.id
    }

    override suspend fun updatePlayer(id: String, request: Player): Boolean =
        getPlayerById(id)
            ?.let { player ->
                val updateResult = players.replaceOneById(
                    ObjectId(id),
                    player.copy(
                        number = request.number,
                        firstName = request.firstName,
                        lastName = request.lastName,
                        birthday = request.birthday,
                        street = request.street,
                        zipcode = request.zipcode,
                        city = request.city,
                        playedGames = request.playedGames,
                        goals = request.goals,
                        yellowCards = request.yellowCards,
                        twoMinutes = request.twoMinutes,
                        redCards = request.redCards
                    )
                )
                updateResult.modifiedCount == 1L
            } ?: false

    override suspend fun deletePlayer(id: String): Boolean {
        val deleteResult = players.deleteOneById(ObjectId(id))
        return deleteResult.deletedCount == 1L
    }

    override suspend fun getPlayersBySearch(name: String): List<Player> =
        players.find(or(Player::lastName regex name, Player::firstName regex name)).toList()
}