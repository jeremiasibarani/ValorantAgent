package com.example.myexpertnews.ui.agents

import androidx.lifecycle.ViewModel
import com.example.core.domain.model.Agent
import com.example.core.domain.usecase.AgentsUseCase
import kotlinx.coroutines.flow.Flow


class FavoriteAgentsViewModel(agentsUseCase: AgentsUseCase) : ViewModel() {
    val favoriteAgents : Flow<List<Agent>> = agentsUseCase.getFavoriteAgent()
}