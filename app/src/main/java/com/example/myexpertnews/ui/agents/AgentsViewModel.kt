package com.example.myexpertnews.ui.agents

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.data.source.remote.NetworkResult
import com.example.core.domain.model.Agent
import com.example.core.domain.model.DetailAgent
import com.example.core.domain.usecase.AgentsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import java.util.concurrent.Executors

class AgentsViewModel(agentsUseCase: AgentsUseCase) : ViewModel(){
    val agents : Flow<NetworkResult<List<Agent>>> = agentsUseCase.getAgents()

}