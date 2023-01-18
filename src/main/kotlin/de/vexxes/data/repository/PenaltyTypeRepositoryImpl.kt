package de.vexxes.data.repository

import de.vexxes.domain.model.PenaltyType
import de.vexxes.domain.repository.PenaltyTypeRepository
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId
import org.litote.kmongo.regex

class PenaltyTypeRepositoryImpl(
    database: CoroutineDatabase
) : PenaltyTypeRepository {
    private val penaltyTypes = database.getCollection<PenaltyType>()

    override suspend fun getAllPenaltyTypes(): List<PenaltyType> =
        penaltyTypes.find().toList()

    override suspend fun getPenaltyTypeById(id: String): PenaltyType? {
        val bsonId: Id<PenaltyType> = ObjectId(id).toId()
        return penaltyTypes.findOne(PenaltyType::id eq bsonId)
    }

    override suspend fun postPenaltyType(penaltyType: PenaltyType): Id<PenaltyType>? {
        penaltyTypes.insertOne(penaltyType)
        return penaltyType.id
    }

    override suspend fun updatePenaltyType(id: String, request: PenaltyType): Boolean =
        getPenaltyTypeById(id)
            ?.let { penaltyType ->
                val updateResult = penaltyTypes.replaceOneById(
                    ObjectId(id),
                    penaltyType.copy(
                        name = request.name,
                        description = request.description,
                        isBeer = request.isBeer,
                        value = request.value
                    )
                )
                updateResult.modifiedCount == 1L
            } ?: false

    override suspend fun deletePenaltyType(id: String): Boolean {
        val deleteResult = penaltyTypes.deleteOneById(ObjectId(id))
        return deleteResult.deletedCount == 1L
    }

    override suspend fun getPenaltyTypeBySearch(name: String): List<PenaltyType> =
        penaltyTypes.find(PenaltyType::name regex name).toList()
}