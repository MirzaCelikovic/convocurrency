package com.example.celik.convocurrency.ui.viewmodels

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.View
import com.example.celik.convocurrency.BR
import com.example.celik.convocurrency.database.AppDatabase
import com.example.celik.convocurrency.model.Currency
import com.example.celik.convocurrency.ui.adapters.AllCurrenciesAdapter
import java.util.*

class AllCurrenciesViewModel(val context: Context) : BaseObservable() {
    private var appDatabase: AppDatabase? = null
    private var emptyStateVisibility = View.GONE
    private var recyclerViewVisibility = View.GONE
    private lateinit var currencyViewModels : ArrayList<CurrencyViewModel>

    private var allCurrenciesAdapter = AllCurrenciesAdapter()

    public fun loadLocalData() {
        if (context == null) {
            return
        }
        appDatabase = AppDatabase.getInstance(context.applicationContext)
        
    }

    private fun loadData(allCurrencies : List<Currency>?) {
        currencyViewModels = ArrayList()
        allCurrenciesAdapter.clearItems()
        if (allCurrencies != null) {
            for (currency in allCurrencies) {
                currencyViewModels.add(CurrencyViewModel(currency.id, currency.currencyName, null, false))
                allCurrenciesAdapter.addItem(CurrencyViewModel(currency.id, currency.currencyName, null, false))
            }
        }

        if (currencyViewModels.isEmpty()) {
            noData()
        } else {
            emptyStateVisibility = View.GONE
            recyclerViewVisibility = View.VISIBLE
            notifyPropertyChanged(BR.emptyStateVisible)
            notifyPropertyChanged(BR.recyclerViewVisible)
        }
    }

    private fun noData() {
        emptyStateVisibility = View.VISIBLE
        recyclerViewVisibility = View.GONE
        notifyPropertyChanged(BR.emptyStateVisible)
        notifyPropertyChanged(BR.recyclerViewVisible)
    }

    @Bindable
    fun getRecyclerViewVisible() : Int {
        return recyclerViewVisibility
    }

    @Bindable
    fun getEmptyStateVisible() : Int {
        return emptyStateVisibility
    }

    @Bindable
    fun getAdapter(): AllCurrenciesAdapter {
        return allCurrenciesAdapter
    }
}