package de.vexxes.domain.repository

import de.vexxes.domain.model.Event
import de.vexxes.domain.model.EventPlayerAvailability
import org.litote.kmongo.Id

interface EventRepository {
    suspend fun getAllEvents(): List<Event>
    suspend fun postEvent(event: Event): Id<Event>?
    suspend fun getEventById(id: String): Event?
    suspend fun updateEvent(id: String, event: Event): Boolean
    suspend fun deleteEvent(id: String): Boolean
    suspend fun playerEvent(id: String, playerState: EventPlayerAvailability): Boolean
}