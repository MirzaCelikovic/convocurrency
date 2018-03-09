package com.example.celik.convocurrency.ui

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.example.celik.convocurrency.BR

class CurrencyViewModel(currencyAbbreviation: String, currencyFullName : String) : BaseObservable() {

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