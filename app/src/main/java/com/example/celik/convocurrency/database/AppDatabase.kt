package com.example.celik.convocurrency.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.celik.convocurrency.model.Currency

@Database(entities = [(Currency::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun allCurrenciesDao() : AllCurrenciesDao

    companion object {
        private var dbInstance : AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase? {
            if (dbInstance == null) {
                synchronized(AppDatabase::class) {
                    dbInstance = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, "appDatabase")
                            .build()
                }
            }
            return dbInstance
        }

        fun destroyInstance() {
            dbInstance = null
        }
    }
}