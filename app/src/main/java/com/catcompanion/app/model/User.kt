package com.catcompanion.app.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.catcompanion.app.db.DatabaseConverters
import java.util.Date

@Entity(tableName = "users")
@TypeConverters(DatabaseConverters::class)
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val createdAt: Date,
    var catApiUserId: String?
)
