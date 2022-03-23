package com.example.credibancoapp.model

import android.widget.TextView
import com.example.credibancoapp.R
import com.xwray.groupie.Item
import com.xwray.groupie.GroupieViewHolder

class AuthorizationItem(
    val commerceCode: String,
    val terminalCode: String,
    val amount: String,
    val card: String,
    val status: String,
    val receiptId: String,
    val rnn: String
): Item<GroupieViewHolder>(){

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val textViewAmount: TextView = viewHolder.itemView.findViewById(R.id.text_view_moto_transaccion)
        val textViewStatus: TextView = viewHolder.itemView.findViewById(R.id.text_view_referencia_transaccion)
        textViewAmount.text = amount
        textViewStatus.text = status
    }

    override fun getLayout() = R.layout.authorization_item
}