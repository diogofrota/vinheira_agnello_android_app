package br.com.fiap.vinheriaagnello.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.fiap.vinheriaagnello.model.Wine

@Database(entities = [Wine::class], version = 1, exportSchema = false)
abstract class WineDatabase : RoomDatabase() {
    abstract fun wineDao(): WineDao

    companion object {
        @Volatile
        private var INSTANCE: WineDatabase? = null

        fun getDatabase(context: Context): WineDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WineDatabase::class.java,
                    "wine_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}