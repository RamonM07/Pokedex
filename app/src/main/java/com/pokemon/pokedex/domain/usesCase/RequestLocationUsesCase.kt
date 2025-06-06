package com.pokemon.pokedex.domain.usesCase

import android.location.Location
import com.pokemon.pokedex.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class RequestLocationPermissionUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(): Flow<Location> {
        return locationRepository.getLocationUpdates()
            .distinctUntilChanged { old, new -> old.distanceTo(new) < 10f }
    }
}
