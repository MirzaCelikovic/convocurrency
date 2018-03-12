package com.example.celik.convocurrency.ui.viewmodels

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.example.celik.convocurrency.BR
import com.example.celik.convocurrency.ui.fragments.ConvertCurrenciesFragment

class CurrencyViewModel(currencyAbbreviation: String, currencyFullName: String, actionCallback: ConvertCurrenciesFragment.ActionCallback?) : BaseObservable() {

    private val currencyAbbreviationString = currencyAbbreviation
    private val currencyFullNameString = currencyFullName

    init {
        notifyPropertyChanged(BR.currencyAbbreviation)
        notifyPropertyChanged(BR.currencyAbbreviation)
    }


    @Bindable
    fun getCurrencyAbbreviation() : String {
        return currencyAbbreviationString
    }

    @Bindable
    fun getCurrencyFullName() : String {
        return currencyFullNameString
    }
}