package com.example.offlineapplicationdemo.model.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.offlineapplicationdemo.model.bean.UserData
import com.example.offlineapplicationdemo.model.room.dao.UserDao

@Database(entities = [UserData::class], version = 1)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var instance: AppRoomDatabase? = null

        operator fun invoke(context: Context): AppRoomDatabase = run {
            instance ?: synchronized(Any()) { createInstance(context).also { instance = it } }
        }

        private fun createInstance(context: Context): AppRoomDatabase = run {
            Room.databaseBuilder(context, AppRoomDatabase::class.java, "app_room_database").build()
        }
    }
}