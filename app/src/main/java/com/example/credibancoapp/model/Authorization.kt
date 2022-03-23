package com.example.credibancoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authorizations_table")
data class Authorization(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "commerce_code_column") var commerceCode: String,
    @ColumnInfo(name = "terminal_code_column") var terminalCode: String,
    @ColumnInfo(name = "amount_column") var amount: String,
    @ColumnInfo(name = "card_column") var card: String,
    @ColumnInfo(name = "receipt_id_column") var receiptId: String,
    @ColumnInfo(name = "rrn_column") var rrn: String,
    @ColumnInfo(name = "status_description_column") var statusDescription: String,
    @ColumnInfo(name = "authorization_status_column") var authorizationStatus: String) {
}