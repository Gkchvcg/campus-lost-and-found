package com.example.campuslostfound.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val category: String,
    val type: String, // "LOST" or "FOUND"
    val location: String,
    val date: String,
    val time: String,
    val imageUri: String?,
    val contactInfo: String,
    val postedDate: Long,
    val recovered: Boolean = false
)
