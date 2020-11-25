package com.example.offlineapplicationdemo.model.bean

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

class UserDataResponse : BaseBean() {
    var data: ArrayList<UserData>? = null
}

@Entity(tableName = "user_data")
data class UserData(
    @PrimaryKey
    val id: String,
    val email: String?,
    val first_name: String?,
    val last_name: String?,
    val avatar: String?,
)