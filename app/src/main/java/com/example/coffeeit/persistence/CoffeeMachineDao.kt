package com.example.coffeeit.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coffeeit.model.*

@Dao
interface CoffeeMachineDao {

    // Coffee Machine data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoffeeMachineData(coffeeMachineData: CoffeeMachine)

    @Query("SELECT * FROM CoffeeMachine")
    suspend fun getCoffeeMachineData(): CoffeeMachine

    // Coffee Types data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoffeeTypeData(coffeeType: List<Type>)

    @Query("SELECT * FROM Type WHERE id IN (:ids)")
    suspend fun getCoffeeTypeData(ids: List<String>): List<Type>

    @Query("SELECT sizes FROM Type WHERE id = :id_")
    suspend fun getCoffeeSizeFromTypeId(id_: String): String?

    @Query("SELECT extras FROM Type WHERE id = :id_")
    suspend fun getCoffeeExtraFromTypeId(id_: String): String?

    // Coffee Sizes data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoffeeSizeData(coffeeSize: List<Size>)

    @Query("SELECT * FROM Size WHERE id IN (:ids_)")
    suspend fun getCoffeeSizeData(ids_: List<String>): List<Size>

    // Coffee Extras data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoffeeExtrasData(coffeeExtras: List<Extra>)

    @Query("SELECT * FROM Extra WHERE id IN (:ids_)")
    suspend fun getCoffeeExtrasData(ids_: List<String>): List<Extra>

    // Coffee SubSelection data
/*    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoffeeSubSelectionData(coffeeSubSelections: List<Subselection>)

    @Query("SELECT * FROM Extra WHERE id IN (:ids)")
    suspend fun getCoffeeSubSelectionData(ids: List<Subselection>): List<Subselection>*/
}
