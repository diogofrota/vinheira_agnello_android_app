package br.com.fiap.vinheriaagnello.database

import androidx.room.*
import br.com.fiap.vinheriaagnello.model.Wine
import kotlinx.coroutines.flow.Flow

@Dao
interface WineDao {
    @Query("SELECT * FROM wines ORDER BY name ASC")
    fun getAllWines(): Flow<List<Wine>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWine(wine: Wine)

    @Update
    suspend fun updateWine(wine: Wine)

    @Delete
    suspend fun deleteWine(wine: Wine)

    @Query("SELECT * FROM wines WHERE id = :id")
    suspend fun getWineById(id: Long): Wine?
}