package com.disney.service

import com.disney.constant.Constant
import com.disney.dao.ThirdPartyDAO
import com.disney.model.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

@Service
class ThirdPartyService(private val thirdPartyDAO: ThirdPartyDAO) {

    private val logger: Logger = LoggerFactory.getLogger(ThirdPartyService::class.java)

    fun getWeatherForecast(): Mono<WeatherResponse> {
        logger.info("Fetching weather forecast...")

        //fetch the data from the provided URL and fill the request then map the request to the response
        return thirdPartyDAO.getWeatherData()
            .map { request ->
                //I am assuming here that the "current day" reading is the first entry on the list of periods
                val periods = request.properties.periods.filter { period -> period.number == 1 }
                    .map { period ->
                    DailyWeather(
                        dayName = getDayName(period.startTime),
                        tempHighCelsius = getTemperatureReading(period.temperature, period.temperatureUnit),
                        forecastBlurp = period.shortForecast
                    )
                }
                WeatherResponse(daily = periods)
            }
            .doOnError {
                //throw a runtime exception if we can't fill the request or map the response
                throw RuntimeException(Constant.WEATHER_EXCEPTION)
            }
    }

    //converts the given temperature to celsius
    private fun getTemperatureReading(temperature: Double, unit: String): Double {
        return when (unit) {
            "F" -> {
                roundToOneDecimal((temperature - 32) * 5 / 9)
            }
            //You never know when someone wants to use Kelvin
            "K" -> {
                roundToOneDecimal(temperature - 273.15)
            }
            else -> {
                roundToOneDecimal(temperature)
            }
        }
    }

    //converting the current day to the day of the week
    private fun getDayName(dateTimeString: String): String {
        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val dateTime = LocalDate.parse(dateTimeString, formatter)
        //Returns the day of the week in the correct format
        return dateTime.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.US)
    }

    //Rounds a value to 1 decimal place
    private fun roundToOneDecimal(decimal: Double): Double {
        return BigDecimal(decimal).setScale(1, RoundingMode.HALF_EVEN).toDouble()
    }
}
