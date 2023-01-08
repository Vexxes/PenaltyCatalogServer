package de.vexxes.data.repository

import de.vexxes.domain.model.*
import de.vexxes.domain.repository.Repository
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.id.toId

@Serializable
data class ResultCount(val count: Int)

class RepositoryImpl(
    database: CoroutineDatabase
) : Repository {

    private val players = database.getCollection<Player>()
    private val penaltyReceived = database.getCollection<PenaltyReceived>()

    override suspend fun getAllPlayers(): List<Player> =
        players.find()
            .toList()

    override suspend fun postPlayer(player: Player): Id<Player>? {
        players.insertOne(player)
        return player.id
    }


    override suspend fun getPlayerById(id: String): Player? {
        val bsonId: Id<Player> = ObjectId(id).toId()
        return players.findOne(Player::id eq bsonId)
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

    override suspend fun getPlayersBySearch(name: String): List<Player> {
        return players.find(or(Player::lastName regex name, Player::firstName regex name)).toList()
    }


    override suspend fun getDeclaredPenalties(penaltyTypeId: Id<PenaltyType>?): ApiResponse {
        // TODO
//        val numberOfDeclaredPenalties = penaltyReceived.aggregate<ResultCount>(
//            match(filter = PenaltyReceived::penaltyTypeId eq penaltyTypeId),
//            group(
//                penaltyTypeId,
//                ResultCount::count sum 1
//            )
//        )

        return ApiResponse(
            success = true,
//            message = numberOfDeclaredPenalties.first()!!.count.toString()
        )
    }


    override suspend fun getAllPenaltyHistory(): ApiResponse {
        return ApiResponse(
            success = true,
            penaltyReceived = penaltyReceived
                .find()
                .sort(
                    descending(PenaltyReceived::timeOfPenalty)
                )
                .toList()
        )
    }

    override suspend fun getPenaltyHistoryById(penaltyReceivedId: Id<PenaltyReceived>?): ApiResponse {
        return ApiResponse(
            success = true,
            penaltyReceived = penaltyReceived.find(filter = PenaltyReceived::_id eq penaltyReceivedId).toList()
        )
    }

    override suspend fun getPenaltyHistoryBySearch(searchText: String): ApiResponse {
        // Parameter has to be replaced, otherwise unnecessary quotation marks are added and the filter won't work
        val searchTextReplace = searchText.replace("\"", "")

        return ApiResponse(
            success = true,
            penaltyReceived = penaltyReceived.find(
                or(
                    /*TODO*/
//                    (PenaltyReceived::penaltyTypeId).regex(searchTextReplace, "i"),
//                    (PenaltyReceived::playerId).regex(searchTextReplace, "i"),
                    /*TODO Implement regex / search for timeOfPenalty*/
                )
            )
                .sort(
                    descending(PenaltyReceived::timeOfPenalty)
                )
                .toList()
        )
    }

    override suspend fun updatePenaltyHistory(penaltyReceived: PenaltyReceived): Boolean {
        return this.penaltyReceived.updateOneById(
            id = penaltyReceived._id,
            update = penaltyReceived,
            options = upsert()
        ).wasAcknowledged()
    }

    override suspend fun deletePenaltyHistory(penaltyReceivedId: Id<PenaltyReceived>?): Boolean {
        return penaltyReceived.deleteOneById(
            id = penaltyReceivedId!!
        ).wasAcknowledged()
    }
}