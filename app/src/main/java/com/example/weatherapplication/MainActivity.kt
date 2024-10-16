package com.example.weatherapplication

import WeatherViewModel
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cityInput = findViewById<EditText>(R.id.cityInput)
        val searchButton = findViewById<Button>(R.id.searchButton)
        val weatherInfo = findViewById<TextView>(R.id.weatherInfo)
        val weatherIcon = findViewById<ImageView>(R.id.weatherIcon) // ImageView for the icon

        // Observe weather data
        viewModel.weatherData.observe(this, Observer { weatherResponse ->
            weatherInfo.text = "Temperature: ${weatherResponse.main.temp}°C\n" +
                               "Humidity: ${weatherResponse.main.humidity}%\n" +
                               "Description: ${weatherResponse.weather[0].description}"

            // Load the weather icon using Glide
            val iconCode = weatherResponse.weather[0].icon
            val iconUrl = "https://openweathermap.org/img/wn/$iconCode@2x.png"
            Glide.with(this)
                .load(iconUrl)
                .into(weatherIcon)
        })

        searchButton.setOnClickListener {
            val city = cityInput.text.toString()
            viewModel.fetchWeather(city)
        }
    }
}