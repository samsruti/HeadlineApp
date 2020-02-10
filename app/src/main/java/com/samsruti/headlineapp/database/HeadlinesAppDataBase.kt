package com.samsruti.headlineapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.samsruti.headlineapp.model.Article


@Database(
    entities = [Article::class],
    version = 1, exportSchema = false
)
abstract class HeadlinesAppDataBase : RoomDatabase() {
    abstract fun getDao(): HeadlinesAppDao

    companion object {
        private const val DB_NAME = "newsApp.db"
        @Volatile
        private var INSTANCE: HeadlinesAppDataBase? = null

        fun getInstance(context: Context): HeadlinesAppDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: createdDB(context).also { INSTANCE = it }
            }

        private fun createdDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                HeadlinesAppDataBase::class.java,
                DB_NAME
            ).build()

    }
}