package com.example.credibancoapp.model

import com.google.gson.annotations.SerializedName

data class AnnulmentResponse(
    @SerializedName("statusCode") val statusCode: String,
    @SerializedName("statusDescription") val statusDescription: String
){
}