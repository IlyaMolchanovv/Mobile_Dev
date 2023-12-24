package com.example.list1110.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.list1110.data.Student
import com.example.list1110.data.Faculty
import com.example.list1110.data.Group


@Database(
    entities = [Faculty::class,
        Group::class,
        Student::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListTypeConverters::class)

abstract class ListDatabase: RoomDatabase() {
    abstract fun listDAO(): ListDAO

    companion object {
        @Volatile
        private var INSTANCE: ListDatabase? = null

        fun getDatabase(context: Context): ListDatabase {
            return INSTANCE ?: synchronized(this) {
                buildDatabase(context).also { INSTANCE = it }
            }
        }
        /*
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                ...
            }
        }
        */
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            ListDatabase::class.java,
            "List_database")
            .fallbackToDestructiveMigration()
            //    .addMigrations(MIGRATION_2_3)
            .build()

    }
}