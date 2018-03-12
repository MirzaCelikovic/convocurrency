package com.example.celik.convocurrency.widget

import android.view.animation.AccelerateDecelerateInterpolator

class ReverseAccelerateDecelerateInterpolator : AccelerateDecelerateInterpolator() {
    override fun getInterpolation(p0: Float): Float {
        return Math.abs(p0 - 1f)
    }
}