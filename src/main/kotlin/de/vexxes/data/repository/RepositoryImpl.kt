package de.vexxes.data.repository

import de.vexxes.domain.model.*
import de.vexxes.domain.repository.Repository
import kotlinx.serialization.Serializable
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.aggregate

@Serializable
data class ResultCount(val count: Int)

class RepositoryImpl(
    database: CoroutineDatabase
) : Repository {

    private val players = database.getCollection<Player>()
    private val categories = database.getCollection<PenaltyCategory>()
    private val penalties = database.getCollection<PenaltyType>()
    private val penaltyReceived = database.getCollection<PenaltyReceived>()

    override suspend fun getAllPlayers(sortOrder: Int): ApiResponse {
        val sortAscDesc = if (sortOrder == 1) ascending(Player::number) else descending(Player::number)
        return ApiResponse(
            success = true,
            player = players.find().sort(sortAscDesc).toList()
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

    override suspend fun getPlayersBySearch(searchText: String): ApiResponse {
        // Parameter has to be replaced, otherwise unnecessary quotation marks are added and the filter won't work
        val searchTextReplace = searchText.replace("\"", "")

        return ApiResponse(
            success = true,
            player = players.find(
                or(
                    (Player::firstName).regex(searchTextReplace, "i"),
                    (Player::lastName).regex(searchTextReplace, "i")
                )
            )
                .sort(ascending(Player::number))
                .toList()
        )
    }


    override suspend fun getAllCategories(): ApiResponse {
        return ApiResponse(
            success = true,
            penaltyCategory = categories.find().toList()
        )
    }

    override suspend fun getAllPenalties(): ApiResponse {
        return ApiResponse(
            success = true,
            penaltyType = penalties
                .find()
                .sort(
                    ascending(PenaltyType::name)
                )
                .toList()
        )
    }

    override suspend fun getPenaltyById(penaltyTypeId: String?): ApiResponse {
        return ApiResponse(
            success = true,
            penaltyType = penalties.find(filter = PenaltyType::_id eq penaltyTypeId).toList()
        )
    }

    override suspend fun getDeclaredPenalties(penaltyTypeId: String): ApiResponse {
        val numberOfDeclaredPenalties = penaltyReceived.aggregate<ResultCount>(
            match(filter = PenaltyReceived::penaltyTypeId eq penaltyTypeId),
            group(
                penaltyTypeId,
                ResultCount::count sum 1
            )
        )

        return ApiResponse(
            success = true,
            message = numberOfDeclaredPenalties.first()!!.count.toString()
        )
    }

    override suspend fun getPenaltiesBySearch(searchText: String): ApiResponse {
        // Parameter has to be replaced, otherwise unnecessary quotation marks are added and the filter won't work
        val searchTextReplace = searchText.replace("\"", "")

        return ApiResponse(
            success = true,
            penaltyType = penalties.find(
                or(
                    (PenaltyType::name).regex(searchTextReplace, "i"),
                    (PenaltyType::categoryID).regex(searchTextReplace, "i")
                )
            )
                .toList()
        )
    }

    override suspend fun updatePenalty(penaltyType: PenaltyType): Boolean {
        return penalties.updateOneById(
            id = penaltyType._id,
            update = penaltyType,
            options = upsert()
        ).wasAcknowledged()
    }

    override suspend fun deletePenalty(penaltyTypeId: String?): Boolean {
        return penalties.deleteOneById(
            id = penaltyTypeId!!
        ).wasAcknowledged()
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

    override suspend fun getPenaltyHistoryById(penaltyReceivedId: String?): ApiResponse {
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
                    (PenaltyReceived::penaltyTypeId).regex(searchTextReplace, "i"),
                    (PenaltyReceived::playerId).regex(searchTextReplace, "i"),
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

    override suspend fun deletePenaltyHistory(penaltyReceivedId: String?): Boolean {
        return penaltyReceived.deleteOneById(
            id = penaltyReceivedId!!
        ).wasAcknowledged()
    }
}