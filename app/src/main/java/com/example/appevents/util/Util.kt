package com.example.appevents.util

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.appevents.R
import java.util.*

class Util() {

    fun dateFormatter(date: Long): String {
        val c = Calendar.getInstance()
        c.timeInMillis = date

        return "${c[Calendar.DAY_OF_MONTH]}/${c[Calendar.MONTH]}/${c[Calendar.YEAR]}"
    }

    fun getEventLocation(latitude: Double, longitude: Double, context: Context): String {
        val geocoder = Geocoder(context)
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)

        if(addresses.isEmpty()) {
            return context.resources.getString(R.string.text_no_location_error)
        }

        return addresses[0].getAddressLine(0)
    }

    fun shareEvent(eventAddress: String, eventPrice: String, context: Context) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "Veja esse evento em $eventAddress por R$$eventPrice")
        sharingIntent.type = "text/plain"
        context.startActivity(Intent.createChooser(sharingIntent, context.resources.getString(R.string.text_share_event)))
    }

    fun isEmailValid(email : String): Boolean {
        val emailSplit = email.split("@")

        return if(emailSplit.size >= 2) {
            email.isNotEmpty() && emailSplit[0].isNotEmpty() && emailSplit[1].isNotEmpty()
        } else {
            false
        }
    }

    fun isNameValid(name: String): Boolean {
        return name.isNotBlank() && name.isNotEmpty()
    }

    fun hideKeyboard(context: Context, view : View){
        val inputMethodManager: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.applicationWindowToken, 0)
    }

    fun setImageGlide(view: View, imgUrl: String, binding: ImageView) {
        Glide
            .with(view)
            .load(imgUrl)
            .placeholder(R.drawable.ic_image_not_found)
            .into(binding)
    }
}