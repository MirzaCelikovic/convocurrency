package com.example.celik.convocurrency.ui.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        appDatabase = AppDatabase.getInstance(this)

        splashImage = findViewById(R.id.app_image)

        loadRemoteData()

        val rotateAnimation = RotateAnimation(360.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotateAnimation.fillAfter = true
        rotateAnimation.duration = 1000
        rotateAnimation.repeatCount = Animation.INFINITE
        rotateAnimation.interpolator = LinearInterpolator()
        rotateAnimation.start()
        splashImage?.animation = rotateAnimation
    }

    private fun loadRemoteData() {
        ApiServiceGenerator.createService(APIService::class.java).getAllCurrencies()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext { result -> saveIntoDatabase(result) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                }, {
                    val dialogBuilder = AlertDialog.Builder(this@SplashActivity)
                    dialogBuilder.setTitle(getString(R.string.error_internet))
                    dialogBuilder.setMessage(getString(R.string.check_connection))
                    dialogBuilder.setNeutralButton(getString(R.string.ok), { dialogInterface, _ ->
                        dialogInterface.dismiss()
                        showMainActivity()
                    })
                    dialogBuilder.show()
                }
                )
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
