package com.example.coffeeit.ui.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.coffeeit.model.CoffeeMachine
import com.example.coffeeit.model.Extra
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    mainRepository: MainRepository
) : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    // fetch data for coffee machine.
    val coffeeMachineData: Flow<CoffeeMachine> =
        mainRepository.loadCoffeeMachine(
            onStart = { _isLoading.value = true },
            onCompletion = { _isLoading.value = false },
            onError = { Timber.d(it) }
        )

    // fetch size ids from selected type Id.
    private val sizeFromTypeIdSharedFlow: MutableSharedFlow<String> = MutableSharedFlow(replay = 1)
    val coffeeSizesByIdFlow = sizeFromTypeIdSharedFlow.flatMapLatest {
        mainRepository.getCoffeeSizesFromId(it)
    }

    fun loadCoffeeSizesByIds(typeId: String) = sizeFromTypeIdSharedFlow.tryEmit(typeId)

    // fetch sizes from size ids.
    private val sizesSharedFlow: MutableSharedFlow<List<String>> = MutableSharedFlow(replay = 1)
    val coffeeSizesFlow = sizesSharedFlow.flatMapLatest {
        mainRepository.getCoffeeSizes(it)
    }

    fun loadCoffeeSizes(sizeIds: List<String>) = sizesSharedFlow.tryEmit(sizeIds)

    // fetch extra ids from selected typeId
    private val extrasFromTypeIdSharedFlow: MutableSharedFlow<String> =
        MutableSharedFlow(replay = 1)
    val coffeeExtrasByIdFlow = extrasFromTypeIdSharedFlow.flatMapLatest {
        mainRepository.getCoffeeExtrasFromId(it)
    }

    fun loadCoffeeExtrasByIds(typeId: String) = extrasFromTypeIdSharedFlow.tryEmit(typeId)

    // fetch extras from extra ids.
    private val extrasSharedFlow: MutableSharedFlow<List<String>> = MutableSharedFlow(replay = 1)
    val coffeeExtrasFlow = extrasSharedFlow.flatMapLatest {
        mainRepository.getCoffeeExtras(it)
    }

    fun loadCoffeeExtras(extraIds: List<String>) = extrasSharedFlow.tryEmit(extraIds)
}
