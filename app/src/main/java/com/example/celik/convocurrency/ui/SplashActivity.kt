package com.example.celik.convocurrency.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.celik.convocurrency.R

class SplashActivity : Activity() {

    private var splashImage: ImageView? = null
    private var splashText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashImage = findViewById(R.id.app_image)
        splashText = findViewById(R.id.app_name)

        ValueAnimator.ofFloat(0f, 300f).apply {
            duration = 1700
            addUpdateListener {
                splashImage?.x = it.animatedValue as Float
                splashText?.y = interpolate(-1000f, 1100f, it.animatedFraction)
            }
            start()

            addListener(object: AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    splashImage?.postDelayed({
                        showMainActivity()
                    }, 500)
                }
            })
        }
    }

    private fun interpolate(fl: Float, fl1: Float, animatedFraction: Float)
            = fl + animatedFraction * (fl1 - fl)

    private fun showMainActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
