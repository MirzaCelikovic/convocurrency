package com.example.celik.convocurrency.ui.fragments

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.celik.convocurrency.R
import com.example.celik.convocurrency.databinding.ConvertCurrenciesFragmentBinding
import com.example.celik.convocurrency.ui.viewmodels.ConvertCurrenciesViewModel

class ConvertCurrenciesFragment : Fragment() {

    private lateinit var convertCurrenciesViewModel : ConvertCurrenciesViewModel
    private var fromCurrency : String = ""
    private var toCurrency : String = ""

    companion object {
        fun getInstance() : ConvertCurrenciesFragment {
            return ConvertCurrenciesFragment()
        }
    }

    interface ActionCallback {
        fun onItemClickFrom(currencyAbbreviationString: String)

        fun onItemClickSecond(currencyAbbreviationString: String)
    }

    private val actionCallback = object : ActionCallback {
        override fun onItemClickFrom(currencyAbbreviationString: String) {
            fromCurrency = currencyAbbreviationString
            convertCurrenciesViewModel.setConversionItems(String.format(getString(R.string.fromXtoY), fromCurrency, ""), fromCurrency, "")
        }

        override fun onItemClickSecond(currencyAbbreviationString: String) {
            toCurrency = currencyAbbreviationString
            convertCurrenciesViewModel.setConversionItems(String.format(getString(R.string.fromXtoY), fromCurrency, toCurrency), fromCurrency, toCurrency)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding : ConvertCurrenciesFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.convert_currencies_fragment, container, false)
        convertCurrenciesViewModel = ConvertCurrenciesViewModel(actionCallback)
        convertCurrenciesViewModel.loadRemoteData()
        binding.data = convertCurrenciesViewModel

        return binding.root
    }
}
