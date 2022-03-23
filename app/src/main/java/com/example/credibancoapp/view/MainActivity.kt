package com.example.credibancoapp.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.credibancoapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val aprobedTransactionsFragment = AprobedTransactionsFragment()
    private val authorizationFragment = AuthorizationFragment.newInstance("","","","","","")
    private var bottomNavigationView: BottomNavigationView? = null

    companion object{
        var transactionType: String = "Authorization"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(aprobedTransactionsFragment)

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView?.setOnItemSelectedListener {
            when(it.itemId){
                R.id.authorization_item -> {
                    transactionType = "Authorization"
                    replaceFragment(authorizationFragment)
                }
                R.id.search_item -> replaceFragment(aprobedTransactionsFragment)
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

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}