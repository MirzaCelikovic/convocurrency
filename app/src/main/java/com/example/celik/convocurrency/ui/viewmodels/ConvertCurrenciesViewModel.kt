package com.example.celik.convocurrency.ui.viewmodels

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.View
import com.example.celik.convocurrency.BR
import com.example.celik.convocurrency.api.APIService
import com.example.celik.convocurrency.api.ApiServiceGenerator
import com.example.celik.convocurrency.model.AllCurrencies
import com.example.celik.convocurrency.ui.adapters.AllCurrenciesAdapter
import com.example.celik.convocurrency.ui.fragments.ConvertCurrenciesFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class ConvertCurrenciesViewModel(private val actionCallback: ConvertCurrenciesFragment.ActionCallback) : BaseObservable() {

    private var emptyStateVisibility = View.GONE
    private var recyclerViewVisibility = View.GONE
    private lateinit var currencyViewModels: ArrayList<CurrencyViewModel>
    private var conversionItems : String = ""
    private var conversionResult : String = ""
    private lateinit var from : String
    private lateinit var to : String

    private var fromAdapter = AllCurrenciesAdapter()
    private var toAdapter = AllCurrenciesAdapter()
    private var amount : String = ""

    fun loadRemoteData() {
        ApiServiceGenerator.createService(APIService::class.java).getAllCurrencies()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ allCurrencies ->
                    loadLocalDataFrom(allCurrencies)
                    loadLocalDataTo(allCurrencies)
                }, {
                    loadLocalDataFrom(AllCurrencies(ArrayList()))
                    loadLocalDataTo(AllCurrencies(ArrayList()))
                })
    }

    private fun loadLocalDataFrom(allCurrencies: AllCurrencies) {
        currencyViewModels = ArrayList()
        fromAdapter.clearItems()
        for (value in allCurrencies.results) {
            currencyViewModels.add(CurrencyViewModel(value.id, value.currencyName, actionCallback, true))
            fromAdapter.addItem(CurrencyViewModel(value.id, value.currencyName, actionCallback, true))
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

    private fun loadLocalDataTo(allCurrencies: AllCurrencies) {
        currencyViewModels = ArrayList()
        toAdapter.clearItems()
        for (value in allCurrencies.results) {
            currencyViewModels.add(CurrencyViewModel(value.id, value.currencyName, actionCallback, false))
            toAdapter.addItem(CurrencyViewModel(value.id, value.currencyName, actionCallback, false))
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

    fun convertValues() {
        val query : String = from + "_" + to + "," + to + "_" + from
        ApiServiceGenerator.createService(APIService::class.java)
                .convert(query)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ convertedCurrencies ->
                    val result = convertedCurrencies.results
                    conversionResult = (result[from + "_" + to]?.value?.times(amount.toDouble())).toString()
                    conversionItems += "\n" + conversionResult
                    notifyPropertyChanged(BR.conversionItems)
                }, {
                    conversionResult = "Unsuccessful"
                })
    }

    fun setAmount(amount : String) {
        this.amount = amount
        notifyPropertyChanged(BR.amount)
    }

    fun setConversionItems(items : String, from : String, to : String) {
        conversionItems = items
        this.from = from
        this.to = to
        notifyPropertyChanged(BR.conversionItems)
    }

    @Bindable
    fun getRecyclerViewVisible(): Int {
        return recyclerViewVisibility
    }

    @Bindable
    fun getEmptyStateVisible(): Int {
        return emptyStateVisibility
    }

    @Bindable
    fun getConversionItems(): String {
        return conversionItems
    }

    @Bindable
    fun getConversionResult(): String {
        return conversionResult
    }

    @Bindable
    fun getAmount() : String {
        return amount
    }

    @Bindable
    fun getFromAdapter(): AllCurrenciesAdapter {
        return fromAdapter
    }

    @Bindable
    fun getToAdapter(): AllCurrenciesAdapter {
        return toAdapter
    }
}