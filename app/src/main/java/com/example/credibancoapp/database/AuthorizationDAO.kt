package com.example.credibancoapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.credibancoapp.model.Authorization

@Dao
interface AuthorizationDAO {

    @Transaction
    @Insert
    suspend fun insert (authorization: Authorization)

    @Delete
    suspend fun delete(authorization: Authorization)

    @Query("UPDATE authorizations_table SET authorization_status_column =:state WHERE receipt_id_column =:receiptId")
    suspend fun updateStatusByReceiptId(receiptId: String, state: String)

    @Query("SELECT * FROM authorizations_table WHERE authorization_status_column =:status")
    fun getAuthorizationsByStatus(status: String?): LiveData<List<Authorization>>

    @Query("SELECT * FROM authorizations_table WHERE id =:number")
    fun getAuthorizationByNumber(number: String?): LiveData<Authorization>
}