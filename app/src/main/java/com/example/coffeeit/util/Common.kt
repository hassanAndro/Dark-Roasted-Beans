package com.example.coffeeit.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

// Convert string from db to List.
fun stringToList(sizeIdString: String): List<String> {
    val type: Type = object : TypeToken<List<String?>?>() {}.type
    return Gson().fromJson<List<String>>(sizeIdString, type)
}