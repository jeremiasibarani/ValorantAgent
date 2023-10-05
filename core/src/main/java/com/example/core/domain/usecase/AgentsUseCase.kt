package com.example.core.domain.usecase

import com.example.core.data.source.remote.NetworkResult
import com.example.core.domain.model.Agent
import com.example.core.domain.model.DetailAgent
import com.example.core.domain.model.Weapon
import kotlinx.coroutines.flow.Flow

interface AgentsUseCase {
    fun getAgents() : Flow<NetworkResult<List<Agent>>>
    fun getAgentById(id : String) : Flow<NetworkResult<DetailAgent>>
    fun addFavoriteAgent(agent : Agent)
    fun removeFavoriteAgent(agent: Agent)
    fun getFavoriteAgent() : Flow<List<Agent>>
    fun checkUser(uuid : String) : Flow<Int>
    fun getWeapons() : Flow<NetworkResult<List<Weapon>>>
}