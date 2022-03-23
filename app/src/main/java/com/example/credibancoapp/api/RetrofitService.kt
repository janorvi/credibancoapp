package com.example.credibancoapp.api

import com.example.credibancoapp.model.AnnulmentRequest
import com.example.credibancoapp.model.AnnulmentResponse
import com.example.credibancoapp.model.AuthorizationRequest
import com.example.credibancoapp.model.AuthorizationResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers

import retrofit2.http.POST

interface RetrofitService {

    companion object {
        private const val BASE_URL = "http://192.168.1.9:8080/api/payments/"
        var retrofitService: RetrofitService? = null
        fun getInstance() : RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }

    @Headers("Authorization: Basic MDAwMTIzMDAwQUJD")
    @POST("authorization")
    suspend fun sendAuthorization(@Body request: AuthorizationRequest): Response<AuthorizationResponse>

    @Headers("Authorization: Basic MDAwMTIzMDAwQUJD")
    @POST("annulment")
    suspend fun cancelAuthorization(@Body request: AnnulmentRequest): Response<AnnulmentResponse>
}