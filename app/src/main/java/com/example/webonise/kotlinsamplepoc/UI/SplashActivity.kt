package com.example.webonise.kotlinsamplepoc.UI

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.webonise.kotlinsamplepoc.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        finishSplashScreen()
    }

    private fun finishSplashScreen() {
        Thread(Runnable {
            Thread.sleep(3000)
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }).start()
    }
}
