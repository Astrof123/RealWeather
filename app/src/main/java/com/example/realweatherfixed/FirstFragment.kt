package com.example.realweatherfixed

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.flexbox.FlexboxLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*




class FirstFragment : Fragment() {
    private lateinit var progressBar: ProgressBar
    private lateinit var leftWrapper: LinearLayout
    private lateinit var rightWrapper: FlexboxLayout


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        progressBar = view.findViewById(R.id.progressBar)
        leftWrapper = view.findViewById(R.id.left_wrapper)
        rightWrapper = view.findViewById(R.id.right_wrapper)

        val currentDate = view.findViewById<TextView>(R.id.currentDate)
        val weather_icon = view.findViewById<ImageView>(R.id.weather_icon)
        val day_temp = view.findViewById<TextView>(R.id.day_temp)

        currentDate.text = getCurrentDateFormatted()

        lifecycleScope.launch {
            try {
                showLoading()

                val API_KEY = "24664d677196183d8f6a0d12e063a503"

                val weather = withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.getWeather(apiKey = API_KEY)
                }
                Log.d("Retrofit", "Weather: $weather")

                day_temp.text = weather.main.temp.toString() + "°"

                if (weather.weather[0].main == "Clouds") {
                    weather_icon.setImageResource(R.drawable.cloud)
                }
                else if (weather.weather[0].main == "Clear") {
                    weather_icon.setImageResource(R.drawable.sunny)
                }
                else {
                    weather_icon.setImageResource(R.drawable.cloudy)
                }

                hideLoading()

            } catch (e: Exception) {
                Log.e("Retrofit", "Error: ${e.message}")
            }
        }

        return view
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        leftWrapper.visibility = View.GONE
        rightWrapper.visibility = View.GONE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
        leftWrapper.visibility = View.VISIBLE
        rightWrapper.visibility = View.VISIBLE
    }
}