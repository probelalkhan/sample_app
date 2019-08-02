package net.simplifiedcoding.data.db.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User(
    var username: String? = null,

    @Ignore
    var password: String? = null
) : Serializable{
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}

