package de.vexxes.domain.repository

import de.vexxes.domain.model.ApiResponse

interface Repository {
    suspend fun getAllPlayers(): ApiResponse
}