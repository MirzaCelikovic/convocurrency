package com.example.celik.convocurrency.util

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView

@BindingAdapter("adapter")
fun adapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}

