package de.vexxes.data.repository

import de.vexxes.domain.model.PenaltyReceived
import de.vexxes.domain.repository.PenaltyReceivedRepository
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId

class PenaltyReceivedRepositoryImpl(
    database: CoroutineDatabase
) : PenaltyReceivedRepository {
    private val penaltiesReceived = database.getCollection<PenaltyReceived>()
    override suspend fun getAllPenaltyReceived(): List<PenaltyReceived> =
        penaltiesReceived.find().toList()

    override suspend fun getPenaltyReceivedById(id: String): PenaltyReceived? {
        val bsonId: Id<PenaltyReceived> = ObjectId(id).toId()
        return penaltiesReceived.findOne(PenaltyReceived::id eq bsonId)
    }

    override suspend fun postPenaltyReceived(penaltyReceived: PenaltyReceived): Id<PenaltyReceived>? {
        penaltiesReceived.insertOne(penaltyReceived)
        return penaltyReceived.id
    }

    override suspend fun updatePenaltyReceived(id: String, request: PenaltyReceived): Boolean =
        getPenaltyReceivedById(id)
            ?.let { penaltyReceived ->
                val updateResult = penaltiesReceived.replaceOneById(
                    ObjectId(id),
                    penaltyReceived.copy(
                        penaltyTypeId = request.penaltyTypeId,
                        playerId = request.playerId,
                        timeOfPenalty = request.timeOfPenalty,
                        timeOfPenaltyPaid = request.timeOfPenaltyPaid
                    )
                )
                updateResult.modifiedCount == 1L
            } ?: false

    override suspend fun deletePenaltyReceived(id: String): Boolean {
        val deleteResult = penaltiesReceived.deleteOneById(ObjectId(id))
        return deleteResult.deletedCount == 1L
    }

}