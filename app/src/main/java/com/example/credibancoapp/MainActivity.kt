package com.example.credibancoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.credibancoapp.fragment.AnnulmentFragment
import com.example.credibancoapp.fragment.AprobedTransactionsFragment
import com.example.credibancoapp.fragment.AuthorizationFragment
import com.example.credibancoapp.fragment.SearchAprobedTransactionsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val annulmentFragment = AnnulmentFragment()
    private val aprobedTransactionsFragment = AprobedTransactionsFragment()
    private val authorizationFragment = AuthorizationFragment()
    private val searchAprobedTransactionsFragment = SearchAprobedTransactionsFragment()
    private var bottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(annulmentFragment)

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView?.setOnItemSelectedListener {
            when(it.itemId){
                R.id.item_1 -> replaceFragment(authorizationFragment)
                R.id.item_2 -> replaceFragment(searchAprobedTransactionsFragment)
                R.id.item_3 -> replaceFragment(aprobedTransactionsFragment)
                R.id.item_4 -> replaceFragment(annulmentFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, fragment)
            transaction.commit();
        }
    }
}