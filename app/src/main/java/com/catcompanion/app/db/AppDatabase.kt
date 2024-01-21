package com.catcompanion.app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.catcompanion.app.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
