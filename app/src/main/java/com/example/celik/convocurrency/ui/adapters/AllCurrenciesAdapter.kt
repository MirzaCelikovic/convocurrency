package com.example.celik.convocurrency.ui.adapters

import com.example.celik.convocurrency.R
import com.example.celik.convocurrency.widget.MultiTypeDataBoundAdapter

class AllCurrenciesAdapter : MultiTypeDataBoundAdapter() {
    override fun getItemLayoutId(position: Int): Int {
        return R.layout.all_currencies_item
    }
}