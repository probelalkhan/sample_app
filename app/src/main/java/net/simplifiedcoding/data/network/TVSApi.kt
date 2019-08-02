package net.simplifiedcoding.data.network

import com.google.gson.JsonObject
import net.simplifiedcoding.data.db.entities.User
import net.simplifiedcoding.data.network.responses.AuthResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface TVSApi {


    @POST("gettabledata.php")
    suspend fun userLogin(
        @Body user: User
    ): Response<AuthResponse>


    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): TVSApi {

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("https://tvsfit.mytvs.in/reporting/vrm/api/test_new/int/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TVSApi::class.java)
        }
    }

}