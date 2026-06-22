package br.com.fiap.vinheriaagnello.database

import br.com.fiap.vinheriaagnello.model.Wine
import kotlinx.coroutines.flow.Flow

class WineRepository(private val wineDao: WineDao) {
    val allWines: Flow<List<Wine>> = wineDao.getAllWines()

    suspend fun insert(wine: Wine) {
        wineDao.insertWine(wine)
    }

    suspend fun update(wine: Wine) {
        wineDao.updateWine(wine)
    }

    suspend fun delete(wine: Wine) {
        wineDao.deleteWine(wine)
    }

    suspend fun getById(id: Long): Wine? {
        return wineDao.getWineById(id)
    }
}