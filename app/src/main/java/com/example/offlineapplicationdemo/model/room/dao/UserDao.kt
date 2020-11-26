package com.example.offlineapplicationdemo.model.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.offlineapplicationdemo.model.bean.UserData
import io.reactivex.rxjava3.core.Single

@Dao
interface UserDao {
    @Query("SELECT * FROM user_data")
    fun getUserList(): Single<List<UserData>>

    /*@Query("SELECT * FROM user_data")
    fun getUserList(): List<UserData>*/

    @Query("DELETE FROM user_data")
    fun deleteAllUsers()

    @Insert
    fun insertAll(users: List<UserData>)
}