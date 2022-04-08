package com.example.coffeeit.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.coffeeit.model.*

@Database(
    entities = [
        CoffeeMachine::class,
        Type::class,
        Size::class,
        Extra::class
    ], version = 1, exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coffeeMachineDao(): CoffeeMachineDao
}
