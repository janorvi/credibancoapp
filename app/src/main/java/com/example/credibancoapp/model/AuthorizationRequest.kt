package com.example.credibancoapp.model

import com.google.gson.annotations.SerializedName

data class AuthorizationRequest(
    @SerializedName("id") val id: String,
    @SerializedName("commerceCode") val commerceCode: String,
    @SerializedName("terminalCode") val terminalCode: String,
    @SerializedName("amount") val amount: String,
    @SerializedName("card") val card: String
) {
}