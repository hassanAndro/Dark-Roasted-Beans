package com.example.coffeeit.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
@Immutable
data class Extra(
    @SerializedName("_id") @PrimaryKey val id: String,
    @SerializedName("name") val name: String? = null,
    @SerializedName("subselections") val subselections: List<Subselection> = listOf()
)