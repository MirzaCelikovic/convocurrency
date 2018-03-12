package com.example.celik.convocurrency.ui.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.RelativeLayout
import com.example.celik.convocurrency.R
import com.example.celik.convocurrency.widget.ReverseAccelerateDecelerateInterpolator

class MainFragment : Fragment() {

    companion object {
        fun getInstance() : MainFragment {
            return MainFragment()
        }

        var menuShown = false
    }

    private lateinit var topView : RelativeLayout
    private lateinit var bottomView : RelativeLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.main_fragment, container, false)
        topView = view.findViewById(R.id.topView) as RelativeLayout
        bottomView = view.findViewById(R.id.bottomView) as RelativeLayout

        topView.setOnClickListener {
            startDisappearAnimation("top")
        }

        bottomView.setOnClickListener {
            startDisappearAnimation("bottom")
        }

        return view
    }

    private fun startDisappearAnimation(from : String) {
        ValueAnimator.ofFloat(0f, -getScreenHeight()).apply {
            addUpdateListener {
                val value = it.animatedValue as Float
                topView.translationY = value
                bottomView.translationY = -value
            }

            interpolator = if (!from.contentEquals("menu")) {
                AccelerateDecelerateInterpolator()
            } else {
                ReverseAccelerateDecelerateInterpolator()
            }

            duration = 750
            start()

            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    menuShown = from.contentEquals("menu")
                    when (from) {
                        "menu" -> menuShown = true
                        "top" -> showConvertCurrenciesFragment()
                        "bottom" -> showAllCurrenciesFragment()
                    }
                }
            })
        }
    }

    private fun getScreenHeight() : Float {
        context ?: return 0f
        val wManager : WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
                ?: return 0f
        val display = wManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.y.toFloat()
    }

    fun showMenu() {
        startDisappearAnimation("menu")
    }

    private fun showAllCurrenciesFragment() {
        activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.root_layout, AllCurrenciesFragment.getInstance(), "AllCurrenciesFragment")
                .addToBackStack(null)
                .commit()
    }

    private fun showConvertCurrenciesFragment() {
        activity.supportFragmentManager
                .beginTransaction()
                .add(R.id.root_layout, ConvertCurrenciesFragment.getInstance(), "ConvertCurrenciesFragment")
                .addToBackStack(null)
                .commit()
    }


}
