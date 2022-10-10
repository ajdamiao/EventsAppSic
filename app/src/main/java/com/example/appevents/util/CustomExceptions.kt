package com.example.appevents.util

import com.example.appevents.exception.CustomException
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.Exception

class CustomExceptions {
    fun Exception.getCustomException(): CustomException?{
        return when(this) {
            is HttpException -> {
                val jsonParsed = JSONObject(this.response()?.errorBody().toString())
                val gson = Gson()
                val cException = gson.fromJson(jsonParsed.toString(), CustomException::class.java)

                cException
            }
            else -> null
        }
    }
}