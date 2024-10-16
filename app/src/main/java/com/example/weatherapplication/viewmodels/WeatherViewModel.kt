// WeatherViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel : ViewModel() {
    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> get() = _weatherData

    private val apiKey = "071908bc91e96eeba551f05f8c420951" // Replace with your actual API key
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val weatherApiService = retrofit.create(WeatherApiService::class.java)

    fun fetchWeather(city: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = weatherApiService.getWeather(city, apiKey)
                _weatherData.postValue(response)
            } catch (e: Exception) {
                // Handle error (e.g., log it or update UI with an error message)
            }
        }
    }
}

