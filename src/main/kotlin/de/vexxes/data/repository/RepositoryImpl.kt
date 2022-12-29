package de.vexxes.data.repository

import de.vexxes.domain.model.*
import de.vexxes.domain.repository.Repository
import kotlinx.serialization.Serializable
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.aggregate
import org.litote.kmongo.regex

@Serializable
data class ResultCount(val count: Int)

class RepositoryImpl(
    database: CoroutineDatabase
): Repository {

    private val players = database.getCollection<Player>()
    private val categories = database.getCollection<PenaltyCategory>()
    private val penalties = database.getCollection<Penalty>()
    private val penaltyHistory = database.getCollection<PenaltyHistory>()

    override suspend fun getAllPlayers(sortOrder: Int): ApiResponse {
        val sortAscDesc = if(sortOrder == 1) ascending(Player::number) else descending(Player::number)
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
            penalty = penalties
                .find()
                .sort(
                    ascending(Penalty::index)
                )
                .toList()
        )
    }

    override suspend fun getPenaltyById(penaltyId: String?): ApiResponse {
        return ApiResponse(
            success = true,
            penalty = penalties.find(filter = Penalty::_id eq penaltyId).toList()
        )
    }

    override suspend fun getDeclaredPenalties(penaltyName: String): ApiResponse {
        val numberOfDeclaredPenalties = penaltyHistory.aggregate<ResultCount>(
            match(filter = PenaltyHistory::penaltyName eq penaltyName),
            group(
                penaltyName,
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
            penalty = penalties.find(
                or(
                    (Penalty::name).regex(searchTextReplace, "i"),
                    (Penalty::categoryName).regex(searchTextReplace, "i")
                )
            )
                .toList()
        )
    }

    override suspend fun updatePenalty(penalty: Penalty): Boolean {
        return penalties.updateOneById(
            id = penalty._id,
            update = penalty,
            options = upsert()
        ).wasAcknowledged()
    }

    override suspend fun deletePenalty(penaltyId: String?): Boolean {
        return penalties.deleteOneById(
            id = penaltyId!!
        ).wasAcknowledged()
    }



    override suspend fun getAllPenaltyHistory(): ApiResponse {
        return ApiResponse(
            success = true,
            penaltyHistory = penaltyHistory
                .find()
                .sort(
                    descending(PenaltyHistory::timeOfPenalty)
                )
                .toList()
        )
    }

    override suspend fun getPenaltyHistoryById(penaltyHistoryId: String?): ApiResponse {
        return ApiResponse(
            success = true,
            penaltyHistory = penaltyHistory.find(filter = PenaltyHistory::_id eq penaltyHistoryId).toList()
        )
    }

    override suspend fun getPenaltyHistoryBySearch(searchText: String): ApiResponse {
        // Parameter has to be replaced, otherwise unnecessary quotation marks are added and the filter won't work
        val searchTextReplace = searchText.replace("\"", "")

        return ApiResponse(
            success = true,
            penaltyHistory = penaltyHistory.find(
                or(
                    (PenaltyHistory::penaltyName).regex(searchTextReplace, "i"),
                    (PenaltyHistory::playerName).regex(searchTextReplace, "i"),
                    /*TODO Implement regex / search for timeOfPenalty*/
                )
            )
                .sort(
                    descending(PenaltyHistory::timeOfPenalty)
                )
                .toList()
        )
    }

    override suspend fun updatePenaltyHistory(penaltyHistory: PenaltyHistory): Boolean {
        return this.penaltyHistory.updateOneById(
            id = penaltyHistory._id,
            update = penaltyHistory,
            options = upsert()
        ).wasAcknowledged()
    }

    override suspend fun deletePenaltyHistory(penaltyHistoryId: String?): Boolean {
        return penaltyHistory.deleteOneById(
            id = penaltyHistoryId!!
        ).wasAcknowledged()
    }
}