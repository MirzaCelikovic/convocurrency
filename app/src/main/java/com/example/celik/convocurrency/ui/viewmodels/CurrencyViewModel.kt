package com.example.celik.convocurrency.ui.viewmodels

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.View
import com.example.celik.convocurrency.BR
import com.example.celik.convocurrency.ui.fragments.ConvertCurrenciesFragment

class CurrencyViewModel(currencyAbbreviation: String, currencyFullName: String, val actionCallback: ConvertCurrenciesFragment.ActionCallback?, val from : Boolean) : BaseObservable() {

    private val currencyAbbreviationString = currencyAbbreviation
    private val currencyFullNameString = currencyFullName

    init {
        notifyPropertyChanged(BR.currencyAbbreviation)
        notifyPropertyChanged(BR.currencyAbbreviation)
    }

    public fun onItemClick() : View.OnClickListener? {
        return View.OnClickListener {
            if (actionCallback != null) {
                 when (from) {
                     true -> actionCallback.onItemClickFrom(currencyAbbreviationString)
                     false -> actionCallback.onItemClickSecond(currencyAbbreviationString)
                 }
            }
        }
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