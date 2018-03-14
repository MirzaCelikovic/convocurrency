package com.example.celik.convocurrency.ui.viewmodels

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.View
import com.example.celik.convocurrency.BR
import com.example.celik.convocurrency.model.Currency
import com.example.celik.convocurrency.ui.fragments.ConvertCurrenciesFragment

class CurrencyViewModel(currency: Currency, private val actionCallback: ConvertCurrenciesFragment.ActionCallback?, val from : Boolean) : BaseObservable() {

    private val currencyAbbreviationString = currency.id
    private val currencyFullNameString = currency.currencyName
    private val currencySymbol : String = currency.currencySymbol

    init {
        notifyPropertyChanged(BR.currencyAbbreviation)
        notifyPropertyChanged(BR.currencyAbbreviation)
        notifyPropertyChanged(BR.currencySymbol)
    }

    fun onItemClick(): View.OnClickListener? {
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
    fun getCurrencyAbbreviation(): String {
        return currencyAbbreviationString
    }

    @Bindable
    fun getCurrencyFullName(): String {
        return currencyFullNameString
    }

    @Bindable
    fun getCurrencySymbol(): String {
        return currencySymbol
    }
}