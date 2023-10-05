package com.example.myexpertnews.ui.agents

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.data.source.remote.NetworkResult
import com.example.core.domain.model.Agent
import com.example.core.domain.usecase.AgentsUseCase
import com.example.core.domain.model.DetailAgent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import java.util.concurrent.Executors.newSingleThreadExecutor

class DetailAgentViewModel (private val agentUseCase: AgentsUseCase) : ViewModel() {

    private val _agentId = MutableStateFlow("")

    private val executor = newSingleThreadExecutor()

    @OptIn(ExperimentalCoroutinesApi::class)
    val detailAgent : Flow<NetworkResult<DetailAgent>> = _agentId.flatMapLatest { id ->
        agentUseCase.getAgentById(id)
    }

    fun setAgentId(id : String){
        _agentId.value = id
    }

    fun addFavoriteAgent(detailAgent : DetailAgent){
        executor.execute {
            agentUseCase.addFavoriteAgent(
                Agent(
                    uuid = detailAgent.uuid,
                    displayName = detailAgent.displayName,
                    imgUrl = detailAgent.imgUrl
                )
            )
        }
    }

    fun removeFavoriteAgent(detailAgent : DetailAgent) {
        executor.execute {
            agentUseCase.removeFavoriteAgent(
                Agent(
                    uuid = detailAgent.uuid,
                    displayName = detailAgent.displayName,
                    imgUrl = detailAgent.imgUrl
                )
            )
        }
    }

    fun checkFavoriteAgent(uuid : String) : LiveData<Int> = agentUseCase.checkUser(uuid).asLiveData()


}