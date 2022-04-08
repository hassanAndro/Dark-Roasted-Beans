package com.example.coffeeit.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun stringToJson(value: List<String>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToString(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun typesToJson(value: List<Type>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToTypes(value: String) = Gson().fromJson(value, Array<Type>::class.java).toList()

    @TypeConverter
    fun sizesToJson(value: List<Size>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToSizes(value: String) = Gson().fromJson(value, Array<Size>::class.java).toList()

    @TypeConverter
    fun extrasToJson(value: List<Extra>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToExtras(value: String) = Gson().fromJson(value, Array<Extra>::class.java).toList()

    @TypeConverter
    fun subSelectionsToJson(value: List<Subselection>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToSubSelections(value: String) =
        Gson().fromJson(value, Array<Subselection>::class.java).toList()

    // Convert string from db to List.
    fun stringToList(idString: String): List<String> {
        val type: java.lang.reflect.Type = object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson<List<String>>(idString, type)
    }
}