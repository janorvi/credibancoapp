package com.example.credibancoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.credibancoapp.R
import com.example.credibancoapp.model.Authorization
import com.example.credibancoapp.viewmodel.AuthorizationFragmentViewModel
import com.example.credibancoapp.viewmodel.AuthorizationFragmentViewModelFactory
import com.example.credibancoapp.model.AnnulmentRequest
import com.example.credibancoapp.model.AuthorizationRequest

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val COMMERCE_CODE = "commerceCode"
private const val TERMINAL_CODE = "terminalCode"
private const val AMOUNT = "amount"
private const val CARD_NUMBER = "cardNumber"
private const val RECEIPT_ID = "receiptId"
private const val RRN = "rrn"
/**
 * A simple [Fragment] subclass.
 * Use the [AuthorizationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AuthorizationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var commerceCode: String? = null
    private var terminalCode: String? = null
    private var amount: String? = null
    private var cardNumber: String? = null
    private var receiptId: String? = null
    private var rrn: String? = null

    private var authorizationViewModel: AuthorizationFragmentViewModel? = null
    private var commerceCodeEditText: EditText? = null
    private var terminalCodeEditText: EditText? = null
    private var amountEditText: EditText? = null
    private var cardEditText: EditText? = null
    private var authorizeButton: Button? = null

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(commerceCode: String, terminalCode: String, amount: String, cardNumber: String, receiptId: String, rrn: String) =
            AuthorizationFragment().apply {
                arguments = Bundle().apply {
                    putString(COMMERCE_CODE, commerceCode)
                    putString(TERMINAL_CODE, terminalCode)
                    putString(AMOUNT, amount)
                    putString(CARD_NUMBER, cardNumber)
                    putString(RECEIPT_ID, receiptId)
                    putString(RRN, rrn)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            commerceCode = it.getString(COMMERCE_CODE)
            terminalCode = it.getString(TERMINAL_CODE)
            amount = it.getString(AMOUNT)
            cardNumber = it.getString(CARD_NUMBER)
            receiptId = it.getString(RECEIPT_ID)
            rrn = it.getString(RRN)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var rootView = inflater.inflate(R.layout.fragment_authorization, container, false)

        var authorizationViewModelFactory: AuthorizationFragmentViewModelFactory? =
            activity?.let { AuthorizationFragmentViewModelFactory.createFactory(it) }

        authorizationViewModel = ViewModelProviders.of(this, authorizationViewModelFactory).get(
            AuthorizationFragmentViewModel::class.java)

        commerceCodeEditText = rootView.findViewById(R.id.commerce_code_edit_text)
        terminalCodeEditText = rootView.findViewById(R.id.terminal_code_edit_text)
        amountEditText = rootView.findViewById(R.id.amount_edit_text)
        cardEditText = rootView.findViewById(R.id.card_number_edit_text)

        authorizeButton = rootView.findViewById(R.id.authorize_button)

        commerceCodeEditText?.setText(commerceCode)
        terminalCodeEditText?.setText(terminalCode)
        amountEditText?.setText(amount)
        cardEditText?.setText(cardNumber)

        if(MainActivity.transactionType == "Annulment"){
            commerceCodeEditText?.isEnabled = false
            terminalCodeEditText?.isEnabled = false
            amountEditText?.isEnabled = false
            cardEditText?.isEnabled = false

            authorizeButton?.text = "Cancel authorization"
        }

        authorizeButton?.setOnClickListener {
            when(MainActivity.transactionType){
                "Authorization" -> {
                    if(commerceCodeEditText?.text?.isEmpty() == true || terminalCodeEditText?.text?.isEmpty() == true || amountEditText?.text?.isEmpty() == true || cardEditText?.text?.isEmpty() == true){
                        Toast.makeText(context,"Some field is empty",Toast.LENGTH_LONG).show()
                    }else {
                        authorizationViewModel?.sendAuthorization(
                            AuthorizationRequest(
                                "001",
                                "000123",
                                "000ABC",
                                "12345",
                                "1234567890123456"
                            )
                        )
                    }
                }
                "Annulment" -> authorizationViewModel?.cancelAuthorization(AnnulmentRequest(receiptId?:"",rrn?:""))
            }
        }

        authorizationViewModel?.authorizationResponse?.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                if (it.statusCode == "00") {
                    authorizationViewModel?.insert(
                        Authorization(
                            0,
                            commerceCodeEditText?.text.toString(),
                            terminalCodeEditText?.text.toString(),
                            amountEditText?.text.toString(),
                            cardEditText?.text.toString(),
                            it.receiptId,
                            it.rnn,
                            it.statusCode,
                            it.statusDescription
                        )
                    )
                    commerceCodeEditText?.setText("")
                    terminalCodeEditText?.setText("")
                    amountEditText?.setText("")
                    cardEditText?.setText("")
                    Toast.makeText(context, "Authorization was sucesfull.", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, "Authorization was failed.", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context, "Authorization was failed.", Toast.LENGTH_SHORT).show()
            }
        })

        authorizationViewModel?.annulmentResponse?.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                if (it.statusCode == "00") {
                    authorizationViewModel?.updateStatusByReceiptId(receiptId ?: "", "Anulada")
                    Toast.makeText(context, "Annulment was sucesfull.", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, "Annulment was failed.", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context, "Annulment was failed.", Toast.LENGTH_SHORT).show()
            }
        })

        return rootView
    }
}