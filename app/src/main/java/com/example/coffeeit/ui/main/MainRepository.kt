package com.example.coffeeit.ui.main

import androidx.annotation.WorkerThread
import com.example.coffeeit.model.CoffeeMachine
import com.example.coffeeit.network.CoffeeMachineService
import com.example.coffeeit.persistence.CoffeeMachineDao
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val coffeeMachineService: CoffeeMachineService,
    private val coffeeMachineDao: CoffeeMachineDao
) {

    @WorkerThread
    fun loadCoffeeMachine(
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String) -> Unit
    ) = flow {
        val coffeeMachineData: CoffeeMachine = coffeeMachineDao.getCoffeeMachineData()
        if (coffeeMachineData == null) {
            // request API network call asynchronously.
            coffeeMachineService.fetchCoffeeList(COFFEE_MACHINE_ID)
                // handle the case when the API request gets a success response.
                .suspendOnSuccess {
                    coffeeMachineDao.insertCoffeeMachineData(data)
                    coffeeMachineDao.insertCoffeeTypeData(data.types)
                    coffeeMachineDao.insertCoffeeSizeData(data.sizes)
                    coffeeMachineDao.insertCoffeeExtrasData(data.extras)
                    emit(data)
                }
                // handle the case when the API request gets an error response.
                // e.g. internal server error.
                .onError {
                    onError(message())
                }
                // handle the case when the API request gets an exception response.
                // e.g. network connection error.
                .onException {
                    onError(message())
                }
        } else {
            emit(coffeeMachineData)
        }
    }.onStart { onStart() }.onCompletion { onCompletion() }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getCoffeeSizesFromId(typeId: String) = flow {
        val coffeeSizesByIds = coffeeMachineDao.getCoffeeSizeFromTypeId(typeId)
        emit(coffeeSizesByIds)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getCoffeeExtrasFromId(typeId: String) = flow {
        val coffeeSizesByIds = coffeeMachineDao.getCoffeeExtraFromTypeId(typeId)
        emit(coffeeSizesByIds)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getCoffeeSizes(sizeIds: List<String>) = flow {
        val coffeeSizes = coffeeMachineDao.getCoffeeSizeData(sizeIds)
        emit(coffeeSizes)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getCoffeeExtras(extraIds: List<String>) = flow {
        val coffeeExtras = coffeeMachineDao.getCoffeeExtrasData(extraIds)
        emit(coffeeExtras)
    }.flowOn(Dispatchers.IO)

    companion object {
        // Coffee machine id
        const val COFFEE_MACHINE_ID = "60ba1ab72e35f2d9c786c610"
    }
}
