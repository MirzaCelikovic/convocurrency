package com.example.celik.convocurrency.ui.fragments

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.celik.convocurrency.R
import com.example.celik.convocurrency.databinding.AllCurrenciesFragmentBinding
import com.example.celik.convocurrency.ui.viewmodels.AllCurrenciesViewModel

class AllCurrenciesFragment : Fragment() {

    private lateinit var allCurrenciesViewModel : AllCurrenciesViewModel

    companion object {
        fun getInstance() : AllCurrenciesFragment {
            return AllCurrenciesFragment()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding : AllCurrenciesFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.all_currencies_fragment, container, false)
        allCurrenciesViewModel = AllCurrenciesViewModel(context)
        allCurrenciesViewModel.loadLocalData()
        binding.data = allCurrenciesViewModel

        return binding.root
    }
}
