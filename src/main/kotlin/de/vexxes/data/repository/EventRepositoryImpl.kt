package de.vexxes.data.repository

import de.vexxes.domain.model.Event
import de.vexxes.domain.model.PlayerState
import de.vexxes.domain.repository.EventRepository
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId

class EventRepositoryImpl(
    database: CoroutineDatabase
) : EventRepository {
    private val events = database.getCollection<Event>()
    override suspend fun getAllEvents(): List<Event> =
        events.find().toList()

    override suspend fun postEvent(event: Event): Id<Event>? {
        events.insertOne(event)
        return event.id
    }

    override suspend fun getEventById(id: String): Event? {
        val bsonId: Id<Event> = ObjectId(id).toId()
        return events.findOne(Event::id eq bsonId)
    }

    override suspend fun updateEvent(id: String, request: Event): Boolean =
        getEventById(id)
            ?.let { event ->
                val updateResult = events.replaceOneById(
                    ObjectId(id),
                    event.copy(
                        title = request.title,
                        startOfEvent = request.startOfEvent,
                        startOfMeeting = request.startOfMeeting,
                        address = request.address,
                        description = request.description,
                        players = request.players
                    )
                )
                updateResult.modifiedCount == 1L
            } ?: false

    override suspend fun deleteEvent(id: String): Boolean {
        val deleteResult = events.deleteOneById(ObjectId(id))
        return deleteResult.deletedCount == 1L
    }

    override suspend fun playerEvent(id: String, playerState: PlayerState): Boolean =
        getEventById(id)
            ?.let { event ->
                val playerList: MutableList<PlayerState> = emptyArray<PlayerState>().toMutableList()
                if (event.players.stream().anyMatch { a -> a.playerId == playerState.playerId }) {
                    event.players.forEach { a -> playerList.add(a) }
                    playerList.removeIf { a -> a.playerId == playerState.playerId }
                    playerList.add(playerState)
                } else {
                    event.players.forEach { a -> playerList.add(a) }
                    playerList.add(playerState)
                }
                val updateResult = events.replaceOneById(
                    ObjectId(id),
                    event.copy(
                        title = event.title,
                        startOfEvent = event.startOfEvent,
                        startOfMeeting = event.startOfMeeting,
                        address = event.address,
                        description = event.description,
                        players = playerList
                    )
                )
                updateResult.modifiedCount == 1L
            } ?: false
}