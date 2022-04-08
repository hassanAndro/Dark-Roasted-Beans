package com.example.coffeeit.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
@Immutable
data class CoffeeMachine(
    @SerializedName("_id") @PrimaryKey val id: String,
    @SerializedName("types") val types: List<Type> = listOf(),
    @SerializedName("sizes") val sizes: List<Size> = listOf(),
    @SerializedName("extras") val extras: List<Extra> = listOf()
) {

    companion object {

        fun mock() = CoffeeMachine(
            id = "60ba1ab72e35f2d9c786c610",
            types = listOf(
                Type(
                    id = "60ba1a062e35f2d9c786c56d",
                    name = "Ristretto",
                    sizes = listOf("60ba18d13ca8c43196b5f606", "60ba3368c45ecee5d77a016b"),
                    extras = listOf("60ba197c2e35f2d9c786c525")
                )
            ),
            sizes = listOf(
                Size(
                    id = "60ba18d13ca8c43196b5f606",
                    name = "Large",
                    _v = 0
                )
            ),
            extras = listOf(
                Extra(
                    id = "60ba197c2e35f2d9c786c525",
                    name = "Select the amount of sugar",
                    subselections = listOf(
                        Subselection(
                            id = "60ba194dfdd5e192e14eaa75",
                            name = "A lot"
                        )
                    )
                )
            )
        )
    }
}