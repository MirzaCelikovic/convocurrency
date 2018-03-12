package com.example.celik.convocurrency.ui.activities

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.example.celik.convocurrency.R
import com.example.celik.convocurrency.ui.fragments.MainFragment

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.root_layout, MainFragment.getInstance(), "MainFragment")
                    .addToBackStack(null)
                    .commit()
        }
    }

//    override fun onBackPressed() {
//        val mainFragment = supportFragmentManager.findFragmentByTag("MainFragment") as MainFragment
//        if (MainFragment.menuShown) {
//            super.onBackPressed()
//            return
//        }
//        mainFragment.showMenu()
//    }
}
