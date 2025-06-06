package com.pokemon.pokedex.presentation.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.pokemon.pokedex.R
import com.pokemon.pokedex.presentation.composables.LocationPermissionDialog
import com.pokemon.pokedex.presentation.theme.PokedexTheme
import com.pokemon.pokedex.presentation.utils.openAppSettings
import com.pokemon.pokedex.presentation.viewModel.PokemonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokedexTheme {
                val viewModel: PokemonViewModel = hiltViewModel()
                val snackBarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()
                val context = LocalContext.current
                var showDialog by remember { mutableStateOf(false) }

                if (showDialog) {
                    LocationPermissionDialog(
                        onDismiss = { showDialog = false },
                        onOpenSettings = {
                            showDialog = false
                            openAppSettings()
                        }
                    )
                }

                val locationPermissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission()
                ) { isGranted ->
                    if (isGranted) {
                        viewModel.onLocationPermissionGranted()
                    } else {
                        scope.launch {
                            snackBarHostState.showSnackbar(getString(R.string.message_without_permissions))
                            showDialog = true
                        }
                    }
                }

                LaunchedEffect(Unit) {
                    if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    } else {
                        viewModel.onLocationPermissionGranted()
                    }
                }

                LaunchedEffect(Unit) {
                    viewModel.showAlert.collect {
                        scope.launch {
                            snackBarHostState.showSnackbar(getString(R.string.message_pokemon_found))
                        }
                    }
                }
                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
                ) { innerPadding ->
                    PokemonScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

