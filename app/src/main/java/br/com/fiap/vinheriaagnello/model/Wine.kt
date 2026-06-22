package br.com.fiap.vinheriaagnello.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wines")
data class Wine(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val type: String,
    val price: Double,
    val quantity: Int
)