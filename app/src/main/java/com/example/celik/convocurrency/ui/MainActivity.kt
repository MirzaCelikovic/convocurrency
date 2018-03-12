package com.example.celik.convocurrency.ui

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.example.celik.convocurrency.R
import com.example.celik.convocurrency.databinding.ActivityMainBinding
import com.example.celik.convocurrency.widget.MultiTypeDataBoundAdapter

class MainActivity : Activity() {

    private var allCurrenciesViewModel = AllCurrenciesViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        allCurrenciesViewModel = AllCurrenciesViewModel()
        allCurrenciesViewModel.loadRemoteData()
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.data  = allCurrenciesViewModel
    }

    class AllCurrenciesAdapter : MultiTypeDataBoundAdapter() {
        override fun getItemLayoutId(position: Int): Int {
            return R.layout.all_currencies_item
        }
    }
}
