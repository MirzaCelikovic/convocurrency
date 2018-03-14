package com.example.celik.convocurrency.ui.activities

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.celik.convocurrency.R
import com.example.celik.convocurrency.api.APIService
import com.example.celik.convocurrency.api.ApiServiceGenerator
import com.example.celik.convocurrency.database.AppDatabase
import com.example.celik.convocurrency.model.AllCurrencies
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SplashActivity : Activity() {
    private var appDatabase: AppDatabase? = null
    private var splashImage: ImageView? = null
    private var splashText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        appDatabase = AppDatabase.getInstance(this)

        splashImage = findViewById(R.id.app_image)
        splashText = findViewById(R.id.app_name)

        loadRemoteData()

        ValueAnimator.ofFloat(0f, 300f).apply {
            duration = 1000
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                splashImage?.rotation = it.animatedValue as Float
            }
            start()
        }
    }

    private fun loadRemoteData() {
        ApiServiceGenerator.createService(APIService::class.java).getAllCurrencies()
                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe { result ->
                    saveIntoDatabase(result)
                }
    }

    private fun saveIntoDatabase(allCurrencies: AllCurrencies?) {
        Observable.create(ObservableOnSubscribe<Unit> {
            emitter -> emitter.onNext(save(allCurrencies))
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    showMainActivity()
                }

    }

    private fun save(allCurrencies: AllCurrencies?) {
        if (allCurrencies == null) {
            return
        }

        for (currency in allCurrencies.results) {
            appDatabase?.allCurrenciesDao()?.insert(currency)
        }
    }

    private fun showMainActivity() {
        splashImage?.animation?.cancel()
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
