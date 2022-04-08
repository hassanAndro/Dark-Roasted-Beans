package com.example.coffeeit.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Immutable
data class Subselection(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String? = null
)