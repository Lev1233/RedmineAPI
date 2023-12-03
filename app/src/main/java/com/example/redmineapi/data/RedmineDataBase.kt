package com.example.redmineapi.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [IssueEntity::class], version = 3, exportSchema = false)
abstract class RedmineDataBase:RoomDatabase() {

    abstract fun issueDao(): IssueDao

    companion object {
        @Volatile
        private var Instance: RedmineDataBase? = null

        fun getDatabase(context: Context): RedmineDataBase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, RedmineDataBase::class.java, "issue_database")
                    /**
                     * Setting this option in your app's database builder means that Room
                     * permanently deletes all data from the tables in your database when it
                     * attempts to perform a migration with no defined migration path.
                     */
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}