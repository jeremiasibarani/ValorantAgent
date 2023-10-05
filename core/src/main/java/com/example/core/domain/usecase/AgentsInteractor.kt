package com.example.core.domain.usecase

import com.example.core.data.source.remote.NetworkResult
import com.example.core.domain.repository.IAgentsRepository
import com.example.core.domain.model.Agent
import com.example.core.domain.model.DetailAgent
import com.example.core.domain.model.Weapon
import kotlinx.coroutines.flow.Flow

class AgentsInteractor(private val agentsRepository : IAgentsRepository) :
    AgentsUseCase {
    override fun getAgents(): Flow<NetworkResult<List<Agent>>> {
        return agentsRepository.getAgent()
    }

    override fun getAgentById(id: String): Flow<NetworkResult<DetailAgent>> {
        return agentsRepository.getAgentById(id)
    }

    override fun addFavoriteAgent(agent: Agent) = agentsRepository.addFavoriteAgent(agent)

    override fun removeFavoriteAgent(agent: Agent) = agentsRepository.removeFavoriteAgent(agent)

    override fun getFavoriteAgent(): Flow<List<Agent>> = agentsRepository.getFavoriteAgent()

    override fun checkUser(uuid: String): Flow<Int> = agentsRepository.checkUser(uuid)

    override fun getWeapons(): Flow<NetworkResult<List<Weapon>>> = agentsRepository.getWeapons()
}