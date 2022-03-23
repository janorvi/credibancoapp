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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.credibancoapp.R
import com.example.credibancoapp.viewmodel.AuthorizationFragmentViewModel
import com.example.credibancoapp.viewmodel.AuthorizationFragmentViewModelFactory
import com.example.credibancoapp.model.AuthorizationItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AprobedTransactionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AprobedTransactionsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var numberEditText: EditText? = null
    private var searchButton: Button? = null

    private var authorizationViewModel: AuthorizationFragmentViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_aprobed_transactions, container, false)

        var authorizationViewModelFactory: AuthorizationFragmentViewModelFactory? =
            activity?.let { AuthorizationFragmentViewModelFactory.createFactory(it) }

        var groupAdapter = GroupAdapter<GroupieViewHolder>()

        var recyclerView: RecyclerView? = null

        numberEditText = rootView.findViewById(R.id.number_edit_text)
        searchButton = rootView.findViewById(R.id.search_button)

        recyclerView = rootView.findViewById(R.id.aprobed_authorizations_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)

        authorizationViewModel = ViewModelProviders.of(this, authorizationViewModelFactory).get(
            AuthorizationFragmentViewModel::class.java)

        authorizationViewModel?.getAuthorizationsByStatus("Aprobada")?.observe(viewLifecycleOwner, Observer {
            for(authorization in it){
                val authorizationItem = AuthorizationItem(authorization.commerceCode, authorization.terminalCode, authorization.amount, authorization.card, authorization.authorizationStatus, authorization.receiptId, authorization.rrn)
                groupAdapter.add(authorizationItem)
            }
            recyclerView.adapter = groupAdapter
        })

        searchButton?.setOnClickListener{
            authorizationViewModel?.getAuthorizationByNumber(numberEditText?.text.toString())?.observe(viewLifecycleOwner, Observer {
                if(it != null) {
                    val authorizationItem = AuthorizationItem(it.commerceCode, it.terminalCode, it.amount, it.card, it.authorizationStatus, it.receiptId, it.rrn)
                    groupAdapter.clear()
                    groupAdapter.add(authorizationItem)
                    recyclerView.adapter = groupAdapter
                }else {
                    Toast.makeText(context,"Don't exists any authorization with the selected number.",Toast.LENGTH_LONG).show()
                }
            })
        }

        groupAdapter.setOnItemClickListener { item, view ->
            var authorizationItem: AuthorizationItem = item as AuthorizationItem

            if(authorizationItem.status == "Aprobada") {
                MainActivity.transactionType = "Annulment"
                activity?.supportFragmentManager?.beginTransaction()?.replace(
                    R.id.frame_layout,
                    AuthorizationFragment.newInstance(
                        authorizationItem.commerceCode,
                        authorizationItem.terminalCode,
                        authorizationItem.amount,
                        authorizationItem.card,
                        authorizationItem.receiptId,
                        authorizationItem.rnn
                    )
                )?.addToBackStack(null)?.commit()
            }else{
                Toast.makeText(context, "This authorization was cancel previously.", Toast.LENGTH_LONG).show()
            }
        }

        Toast.makeText(context, "Put a number to search some transaction or select some authorization in the list to cancel it.", Toast.LENGTH_LONG).show()

        return rootView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AprobedTransactionsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AprobedTransactionsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}