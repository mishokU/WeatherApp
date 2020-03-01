package com.example.weatherapp.domain.convertors

import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.example.weatherapp.R
import com.example.weatherapp.data.remote.retrofitBuilder.IMG_PATH
import com.example.weatherapp.domain.utils.ConnectionStatus
import com.squareup.picasso.Picasso
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


@BindingAdapter("imageUrl")
fun bindImg(imageView: ImageView, img_url: String?){
    Picasso.get().load("$IMG_PATH$img_url.png").placeholder(R.drawable.no_80px).into(imageView)
}

@BindingAdapter("weatherTemp")
fun bindWeatherTemp(textView: TextView, temp: Double?){
    if (temp != null) {
        textView.text = (temp - 273).toInt().toString() + "°"
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter ("currentDate")
fun bindDate(textView: TextView, date : Int){
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
    val formatted = current.format(formatter)
    textView.text = formatted
}

@BindingAdapter("weatherHumidity")
fun bindHumidity(textView: TextView, humidity: Int){
    textView.text = (humidity / 0.1).toString()
}

@BindingAdapter("weatherVisibility")
fun bindVisibility(textView: TextView, visibility: Int){
    textView.text = (visibility / 10000).toString() + "Km"
}

@BindingAdapter("weatherPressure")
fun bindPressure(textView: TextView, pressure: Int){
    textView.text = (pressure * 0.3335).toInt().toString()
}

@BindingAdapter("handleRise")
fun bindRise(textView: TextView, rise : Int){
    val sdf = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val date = java.util.Date(rise.toLong() * 1000)
    sdf.timeZone = TimeZone.getTimeZone("GTM-4")
    textView.text = sdf.format(date)
}

@BindingAdapter("apiStatus")
fun bindStatus(textView: TextView, status: ConnectionStatus?){
    when(status){
        ConnectionStatus.LOADING -> {
            textView.visibility = View.VISIBLE
        }
        ConnectionStatus.ERROR -> {
            textView.visibility = View.VISIBLE
        }
        ConnectionStatus.DONE -> {
            textView.visibility = View.GONE
        }
    }
}

