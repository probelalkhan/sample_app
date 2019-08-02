package net.simplifiedcoding.data.db.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

@Entity
data class Employee(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val designation: String,
    val city: String,
    val joiningDate: String,
    val salary: String,
    var image: String?,
    var imageTime: String?
) : Serializable