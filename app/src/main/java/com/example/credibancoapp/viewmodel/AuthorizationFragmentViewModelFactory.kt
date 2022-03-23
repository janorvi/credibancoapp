package com.example.credibancoapp.viewmodel

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalStateException
import java.lang.RuntimeException

class AuthorizationFragmentViewModelFactory(
    var authorizationRepository: AuthorizationRepository?
): ViewModelProvider.Factory {
    companion object {
        fun createFactory(activity: Activity): AuthorizationFragmentViewModelFactory {
            var context: Context? = null
            context = activity.applicationContext
            if(context == null){
                throw IllegalStateException("Not yet atached to application")
            }
            return AuthorizationFragmentViewModelFactory(
                AuthorizationRepository.getInstance(context)
            )
        }
    }
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try{
            return modelClass.getConstructor(AuthorizationRepository::class.java).newInstance(authorizationRepository)
        }catch (e: InstantiationException){
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        }
    }
}