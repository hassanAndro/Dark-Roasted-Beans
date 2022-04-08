package com.example.coffeeit.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.coffeeit.R
import com.example.coffeeit.ui.theme.CoffeeItComposeTheme
import com.example.coffeeit.util.ConnectionState
import com.example.coffeeit.util.connectivityState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { CoffeeItComposeTheme { CoffeeMachineMainScreen() } }
    }
}
