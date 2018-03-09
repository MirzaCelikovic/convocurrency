package com.example.celik.convocurrency.ui

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import com.example.celik.convocurrency.BR
import com.example.celik.convocurrency.R
import com.example.celik.convocurrency.databinding.ActivityMainBinding
import com.example.celik.convocurrency.widget.BaseDataBoundAdapter
import com.example.celik.convocurrency.widget.DataBoundViewHolder

class MainActivity : Activity() {

    private var allCurrenciesViewModel = AllCurrenciesViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        allCurrenciesViewModel = AllCurrenciesViewModel()
        allCurrenciesViewModel.loadRemoteData()
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.data  = allCurrenciesViewModel
    }

    class AllCurrenciesAdapter(private val currencyViewModels: ArrayList<CurrencyViewModel>?) : BaseDataBoundAdapter<ViewDataBinding>() {

        override fun bindItem(holder: DataBoundViewHolder<ViewDataBinding>?, position: Int, payloads: MutableList<Any>?) {
            holder?.binding?.setVariable(BR.data, currencyViewModels?.get(position))
        }

        override fun getItemCount(): Int {
            if (currencyViewModels?.size == null) {
                return 0
            }
            return currencyViewModels.size
        }

        override fun getItemLayoutId(position: Int): Int {
            return R.layout.all_currencies_item
        }
    }
}
