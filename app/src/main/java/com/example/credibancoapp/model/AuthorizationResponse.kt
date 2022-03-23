package com.example.credibancoapp.model

import com.google.gson.annotations.SerializedName

data class AuthorizationResponse(
    @SerializedName("receiptId") val receiptId: String,
    @SerializedName("rrn") val rnn: String,
    @SerializedName("statusCode") val statusCode: String,
    @SerializedName("statusDescription") val statusDescription: String
) {
}