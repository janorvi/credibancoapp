package com.example.credibancoapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.credibancoapp.api.RetrofitService
import com.example.credibancoapp.database.AuthorizationDAO
import com.example.credibancoapp.database.AuthorizationDatabase
import com.example.credibancoapp.model.AnnulmentRequest
import com.example.credibancoapp.model.Authorization
import com.example.credibancoapp.model.AuthorizationRequest

class AuthorizationRepository(
    private var authorizationDAO: AuthorizationDAO?
) {
    companion object{
        var INSTANCE: AuthorizationRepository? = null
        fun getInstance(context: Context): AuthorizationRepository? {
            if (INSTANCE == null){
                synchronized(AuthorizationRepository::class){
                    if (INSTANCE == null){
                        var authorizationDatabase: AuthorizationDatabase? =
                            AuthorizationDatabase.getInstance(context)
                        INSTANCE =
                            AuthorizationRepository(
                                authorizationDatabase?.authorizationDao()
                            )
                    }
                }
            }
            return INSTANCE
        }
    }

    suspend fun insert(authorization: Authorization) = authorizationDAO?.insert(authorization)

    suspend fun updateStatusByReceiptId(receiptId: String, status: String,) = authorizationDAO?.updateStatusByReceiptId(receiptId, status)

    suspend fun delete(authorization: Authorization) = authorizationDAO?.delete(authorization)

    fun getAuthorizationsByStatus(status: String): LiveData<List<Authorization>> = authorizationDAO?.getAuthorizationsByStatus(status)!!

    fun getAuthorizationByNumber(number: String): LiveData<Authorization> = authorizationDAO?.getAuthorizationByNumber(number)!!

    suspend fun sendAuthorization(authorizationRequest: AuthorizationRequest) = RetrofitService.getInstance().sendAuthorization(authorizationRequest)

    suspend fun cancelAuthorization(annulmentRequest: AnnulmentRequest) = RetrofitService.getInstance().cancelAuthorization(annulmentRequest)
}