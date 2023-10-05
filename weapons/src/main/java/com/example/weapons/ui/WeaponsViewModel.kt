package com.example.weapons.ui

import androidx.lifecycle.ViewModel
import com.example.core.data.source.remote.NetworkResult
import com.example.core.domain.usecase.AgentsUseCase
import com.example.core.domain.model.Weapon
import kotlinx.coroutines.flow.Flow

class WeaponsViewModel (agentsUseCase: AgentsUseCase) : ViewModel() {

    val weapons : Flow<NetworkResult<List<Weapon>>> = agentsUseCase.getWeapons()

}