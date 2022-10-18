package com.web.testtask.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.web.testtask.R
import com.web.testtask.data.repository.WeatherRepository

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}