package com.example.celik.convocurrency.ui.viewmodels

import android.app.AlertDialog
import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.View
import com.example.celik.convocurrency.BR
import com.example.celik.convocurrency.R
import com.example.celik.convocurrency.api.APIService
import com.example.celik.convocurrency.api.ApiServiceGenerator
import com.example.celik.convocurrency.database.AppDatabase
import com.example.celik.convocurrency.model.Currency
import com.example.celik.convocurrency.ui.adapters.AllCurrenciesAdapter
import com.example.celik.convocurrency.ui.fragments.ConvertCurrenciesFragment
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal
import java.util.*

class ConvertCurrenciesViewModel(val context: Context, private val actionCallback: ConvertCurrenciesFragment.ActionCallback) : BaseObservable() {
    private var appDatabase: AppDatabase? = null
    private var emptyStateVisibility = View.GONE
    private var recyclerViewVisibility = View.GONE
    private lateinit var currencyViewModels: ArrayList<CurrencyViewModel>
    private var conversionItems : String = ""
    private var conversionResult : String = ""
    private var from : String = ""
    private var to : String = ""

    private var fromAdapter = AllCurrenciesAdapter()
    private var toAdapter = AllCurrenciesAdapter()
    private var amount : String = ""

    fun loadRemoteData() {
        appDatabase = AppDatabase.getInstance(context.applicationContext)

        Observable.create(ObservableOnSubscribe<List<Currency>> { emitter ->
            emitter.onNext(appDatabase?.allCurrenciesDao()?.getAllCurrencies())
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    loadLocalDataFrom(result)
                    loadLocalDataTo(result)
                }
    }

    private fun loadLocalDataFrom(allCurrencies: List<Currency>) {
        currencyViewModels = ArrayList()
        fromAdapter.clearItems()
        for (value in allCurrencies) {
            currencyViewModels.add(CurrencyViewModel(value, actionCallback, true))
            fromAdapter.addItem(CurrencyViewModel(value, actionCallback, true))
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

    private fun loadLocalDataTo(allCurrencies: List<Currency>) {
        currencyViewModels = ArrayList()
        toAdapter.clearItems()
        for (value in allCurrencies) {
            currencyViewModels.add(CurrencyViewModel(value, actionCallback, false))
            toAdapter.addItem(CurrencyViewModel(value, actionCallback, false))
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
        if ((from.isNullOrBlank()) || to.isNullOrBlank()) {
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setTitle(context.getString(R.string.currency_not_selected))
            dialogBuilder.setMessage(context.getString(R.string.select_currency))
            dialogBuilder.setNeutralButton(context.getString(R.string.ok), {
                dialogInterface, _ -> dialogInterface.dismiss()
            })
            dialogBuilder.create().show()
            return
        }
        val query : String = from + "_" + to + "," + to + "_" + from
        ApiServiceGenerator.createService(APIService::class.java)
                .convert(query)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ convertedCurrencies ->
                    val result = convertedCurrencies.results
                    conversionResult = (result[from + "_" + to]?.value?.times(amount.toDouble()))?.roundTo2DecimalPlaces().toString()
                    conversionItems += "\n" + conversionResult
                    notifyPropertyChanged(BR.conversionItems)
                }, {
                    val dialogBuilder = AlertDialog.Builder(context)
                    dialogBuilder.setTitle(context.getString(R.string.error_internet))
                    dialogBuilder.setMessage(context.getString(R.string.check_connection))
                    dialogBuilder.setNeutralButton(context.getString(R.string.ok), { dialogInterface, _ ->
                        dialogInterface.dismiss()
                    })
                    dialogBuilder.create().show()
                    conversionResult = "Unsuccessful"
                })
    }

    private fun Double.roundTo2DecimalPlaces(): Double = BigDecimal(this).setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()

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