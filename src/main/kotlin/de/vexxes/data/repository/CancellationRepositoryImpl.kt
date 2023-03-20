package de.vexxes.data.repository

import de.vexxes.domain.model.Cancellation
import de.vexxes.domain.model.Event
import de.vexxes.domain.model.Player
import de.vexxes.domain.repository.CancellationRepository
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId

class CancellationRepositoryImpl(
    database: CoroutineDatabase
) : CancellationRepository {
    private val cancellation = database.getCollection<Cancellation>()
    override suspend fun getAllCancellation(): List<Cancellation> =
        cancellation.find().toList()

    override suspend fun getCancellationById(id: String): Cancellation? {
        val bsonId: Id<Cancellation> = ObjectId(id).toId()
        return cancellation.findOne(Cancellation::id eq bsonId)
    }

    override suspend fun getCancellationForEvent(eventId: String): List<Cancellation> {
        val bsonId: Id<Event> = ObjectId(eventId).toId()
        return cancellation.find(Cancellation::eventId eq bsonId).toList()
    }

    override suspend fun getCancellationForPlayer(playerId: String): List<Cancellation> {
        val bsonId: Id<Player> = ObjectId(playerId).toId()
        return cancellation.find(Cancellation::playerId eq bsonId).toList()
    }

    override suspend fun postCancellation(cancellation: Cancellation): Id<Cancellation>? {
        this.cancellation.insertOne(cancellation)
        return cancellation.id
    }

    override suspend fun updateCancellation(id: String, request: Cancellation): Boolean =
        getCancellationById(id)
            ?.let { cancellation ->
                val updateResult = this.cancellation.replaceOneById(
                    ObjectId(id),
                    cancellation.copy(
                        playerId = request.playerId,
                        eventId = request.eventId,
                        timeOfCancellation = request.timeOfCancellation
                    )
                )
                updateResult.modifiedCount == 1L
            } ?: false

    override suspend fun deleteCancellation(id: String): Boolean {
        val deleteResult = cancellation.deleteOneById(ObjectId(id))
        return deleteResult.deletedCount == 1L
    }
}