package net.simplifiedcoding.data.db.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class User(
    var username: String? = null,

    @Ignore
    var password: String? = null
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}

