package net.simplifiedcoding.data.network.responses

import com.google.gson.annotations.SerializedName

class AuthResponse(
    @SerializedName("TABLE_DATA")
    val data: String?,

    @SerializedName("ErrorDescription")
    val errorDescription: String?,

    @SerializedName("Error")
    val error: String?
)