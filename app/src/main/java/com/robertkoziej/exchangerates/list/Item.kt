package com.robertkoziej.exchangerates.list

import com.google.gson.annotations.SerializedName

data class Item(@SerializedName ("date") val date : String,
                @SerializedName ("rates") val rates : kotlin.Any)