package com.catcompanion.app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.catcompanion.app.model.Breed
import com.catcompanion.app.model.User
import java.util.Date

class DatabaseConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}

@Database(entities = [User::class, Breed::class], version = 1, exportSchema = false)
@TypeConverters(DatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun breedDao(): BreedDao

    companion object {
        // The value of a volatile variable will never be cached, and all writes and reads will be done to and from the main memory.
        // This helps make sure the value of INSTANCE is always up-to-date and the same for all execution threads.
        // It means that changes made by one thread to INSTANCE are visible to all other threads immediately.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            // only one thread of execution at a time can enter this block of code
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "cat_companion_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
