package com.example.appevents.model

import com.google.gson.annotations.SerializedName

data class Event (
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("date")
    val date: Long,
    @SerializedName("people")
    val people: List<String>,
    @SerializedName("price")
    val price: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("latitude")
    val latitude: Double,
)