package br.com.fiap.vinheriaagnello.viewmodel

import android.app.Application
import androidx.lifecycle.*
import br.com.fiap.vinheriaagnello.database.WineDatabase
import br.com.fiap.vinheriaagnello.database.WineRepository
import br.com.fiap.vinheriaagnello.model.Wine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WineViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: WineRepository
    val allWines: Flow<List<Wine>>

    init {
        val wineDao = WineDatabase.getDatabase(application).wineDao()
        repository = WineRepository(wineDao)
        allWines = repository.allWines
    }

    fun insertWine(wine: Wine) {
        viewModelScope.launch {
            repository.insert(wine)
        }
    }

    fun updateWine(wine: Wine) {
        viewModelScope.launch {
            repository.update(wine)
        }
    }

    fun deleteWine(wine: Wine) {
        viewModelScope.launch {
            repository.delete(wine)
        }
    }

    suspend fun getWineById(id: Long): Wine? {
        return repository.getById(id)
    }
}