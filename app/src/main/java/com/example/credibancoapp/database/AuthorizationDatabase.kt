package com.example.credibancoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.credibancoapp.model.Authorization

@Database(entities = [Authorization::class], version = 1, exportSchema = false)
abstract class AuthorizationDatabase: RoomDatabase() {

    abstract fun authorizationDao(): AuthorizationDAO

    companion object{

        var INSTANCE: AuthorizationDatabase? = null

        fun getInstance(context: Context): AuthorizationDatabase? {
            if (INSTANCE == null){
                synchronized(AuthorizationDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AuthorizationDatabase::class.java, "myDBAuthorizations")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}