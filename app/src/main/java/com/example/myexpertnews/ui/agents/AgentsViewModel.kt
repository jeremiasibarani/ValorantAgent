package com.example.myexpertnews.ui.agents

import androidx.lifecycle.ViewModel
import com.example.core.data.source.remote.NetworkResult
import com.example.core.domain.model.Agent
import com.example.core.domain.usecase.AgentsUseCase
import kotlinx.coroutines.flow.Flow

class AgentsViewModel(agentsUseCase: AgentsUseCase) : ViewModel(){
    val agents : Flow<NetworkResult<List<Agent>>> = agentsUseCase.getAgents()
}