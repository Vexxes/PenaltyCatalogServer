package de.vexxes.di

import com.mongodb.ConnectionString
import de.vexxes.data.repository.PenaltyReceivedRepositoryImpl
import de.vexxes.data.repository.PenaltyTypeRepositoryImpl
import de.vexxes.data.repository.PlayerRepositoryImpl
import de.vexxes.domain.repository.PenaltyReceivedRepository
import de.vexxes.domain.repository.PenaltyTypeRepository
import de.vexxes.domain.repository.PlayerRepository
import de.vexxes.util.Constants.DATABASE_NAME
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val koinModule = module {
    single {
        KMongo.createClient(ConnectionString("mongodb://penalty-database:27017/"))
            .coroutine
            .getDatabase(DATABASE_NAME)
    }

    single<PlayerRepository> {
        PlayerRepositoryImpl(get())
    }

    single<PenaltyTypeRepository> {
        PenaltyTypeRepositoryImpl(get())
    }

    single<PenaltyReceivedRepository> {
        PenaltyReceivedRepositoryImpl(get())
    }

}