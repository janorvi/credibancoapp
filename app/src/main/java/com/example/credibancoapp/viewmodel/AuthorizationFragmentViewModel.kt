package com.example.credibancoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.credibancoapp.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthorizationFragmentViewModel(
    var authorizationRepository: AuthorizationRepository
): ViewModel() {

    val authorizationResponse: MutableLiveData<AuthorizationResponse> = MutableLiveData()

    val annulmentResponse: MutableLiveData<AnnulmentResponse> = MutableLiveData()

    fun insert(authorization: Authorization){
        CoroutineScope(Dispatchers.IO).launch {
            var result = authorizationRepository.insert(authorization)
        }
    }

    fun delete(authorization: Authorization){
        CoroutineScope(Dispatchers.IO).launch {
            authorizationRepository.delete(authorization)
        }
    }

    fun updateStatusByReceiptId(receiptId: String, status: String){
        CoroutineScope(Dispatchers.IO).launch {
            authorizationRepository.updateStatusByReceiptId(receiptId, status)
        }
    }

    fun getAuthorizationsByStatus(status: String): LiveData<List<Authorization>> = authorizationRepository.getAuthorizationsByStatus(status)

    fun getAuthorizationByNumber(number: String): LiveData<Authorization> = authorizationRepository.getAuthorizationByNumber(number)

    fun sendAuthorization(authorization: AuthorizationRequest){
        CoroutineScope(Dispatchers.IO).launch {
            val response = authorizationRepository.sendAuthorization(authorization)
            if(response != null){
                authorizationResponse?.postValue(response.body())
            }
        }
    }

    fun cancelAuthorization(annulment: AnnulmentRequest){
        CoroutineScope(Dispatchers.IO).launch {
            val response = authorizationRepository.cancelAuthorization(annulment)
            if(response != null){
                annulmentResponse?.postValue(response.body())
            }
        }
    }
}