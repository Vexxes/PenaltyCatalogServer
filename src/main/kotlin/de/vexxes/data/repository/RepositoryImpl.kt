package de.vexxes.data.repository

import de.vexxes.domain.model.ApiResponse
import de.vexxes.domain.model.Penalty
import de.vexxes.domain.model.PenaltyCategory
import de.vexxes.domain.model.Player
import de.vexxes.domain.repository.Repository
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.regex

class RepositoryImpl(
    database: CoroutineDatabase
): Repository {

    private val players = database.getCollection<Player>()
    private val categories = database.getCollection<PenaltyCategory>()
    private val penalties = database.getCollection<Penalty>()

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
            penalty = penalties.find().toList()
        )
    }

    override suspend fun getPenaltyById(penaltyId: String?): ApiResponse {
        return ApiResponse(
            success = true,
            penalty = penalties.find(filter = Penalty::_id eq penaltyId).toList()
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
                    (Penalty::nameOfCategory).regex(searchTextReplace, "i")
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
}
