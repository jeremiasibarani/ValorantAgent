package com.example.core.utils

import com.example.core.data.source.local.AgentsEntity
import com.example.core.data.source.remote.response.AgentByIdResponse
import com.example.core.data.source.remote.response.AgentsResponse
import com.example.core.data.source.remote.response.WeaponsResponse
import com.example.core.domain.model.Agent
import com.example.core.domain.model.DetailAgent
import com.example.core.domain.model.Weapon

object DataMapper {
    fun mapResponseToListDomain(agentsResponse: AgentsResponse) : List<Agent> = agentsResponse.data.map {
        Agent(
            uuid = it.uuid,
            displayName = it.displayName,
            imgUrl = it.fullPortrait ?: it.fullPortraitV2 ?: ""
        )
    }

    fun mapResponseToDomain(agentsByIdResponse: AgentByIdResponse) : DetailAgent = DetailAgent(
        uuid = agentsByIdResponse.data.uuid,
        displayName = agentsByIdResponse.data.displayName,
        imgUrl = agentsByIdResponse.data.fullPortrait ?: agentsByIdResponse.data.fullPortraitV2 ?: "",
        description = agentsByIdResponse.data.description,
        role = agentsByIdResponse.data.role.displayName
    )

    fun mapEntitiesToDomain(agents : List<AgentsEntity>) : List<Agent> = agents.map {
        Agent(
            uuid = it.uuid,
            imgUrl = it.imgUrl,
            displayName = it.displayName
        )
    }

    fun mapDomainToEntity(agent : Agent) : AgentsEntity = AgentsEntity(
        uuid = agent.uuid,
        imgUrl = agent.imgUrl,
        displayName = agent.displayName
    )

    fun mapResponseToListDomain(weaponsResponse: WeaponsResponse) : List<Weapon> = weaponsResponse.data.map {
        Weapon(
            uuid = it.uuid,
            displayName = it.displayName,
            imgUrl = it.displayIcon ?: ""
        )
    }
}