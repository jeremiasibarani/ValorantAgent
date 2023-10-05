package com.example.core.data


import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.example.core.data.source.local.FavoriteAgentsDao
import com.example.core.data.source.remote.AgentsApiService
import com.example.core.data.source.remote.NetworkResult
import com.example.core.domain.model.Agent
import com.example.core.domain.model.DetailAgent
import com.example.core.domain.repository.IAgentsRepository
import com.example.core.domain.model.Weapon
import com.example.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class AgentsRepository(
    private val agentsApiService: AgentsApiService,
    private val favoriteAgentsDao: FavoriteAgentsDao
) : IAgentsRepository {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun getAgent(): Flow<NetworkResult<List<Agent>>> = flow {
        emit(NetworkResult.Loading)
        try{
            val response = agentsApiService.getAgents()
            val responseBody = response.body()
            Log.d("Agents-TAG", "message : ${response.message()}")
            Log.d("Agents-TAG", "code : ${response.code()}")
            Log.d("Agents-TAG", "raw : ${response.raw()}")
            if(response.isSuccessful && responseBody != null){
                emit(NetworkResult.Success(DataMapper.mapResponseToListDomain(responseBody)))
            }else{
                emit(NetworkResult.Error(response.message()))
            }
        }catch (ex : Exception){
            Log.d("Agents-TAG", "raw : ${ex.message.toString()}")
            emit(NetworkResult.Error(ex.message.toString()))
        }
    }

    override fun getAgentById(id: String): Flow<NetworkResult<DetailAgent>> = flow{
        emit(NetworkResult.Loading)
        try{
            val response = agentsApiService.getAgentById(id)
            val responseBody = response.body()
            if(response.isSuccessful && responseBody != null){
                emit(NetworkResult.Success(DataMapper.mapResponseToDomain(responseBody)))
            }else{
                emit(NetworkResult.Error(response.message()))
            }
        }catch (ex : Exception){
            emit(NetworkResult.Error(ex.message.toString()))
        }
    }

    override fun addFavoriteAgent(agent: Agent){
        val agentEntity = DataMapper.mapDomainToEntity(agent)
        favoriteAgentsDao.insertFavoriteAgents(agentEntity)
    }

    override fun removeFavoriteAgent(agent: Agent){
        val agentEntity = DataMapper.mapDomainToEntity(agent)
        favoriteAgentsDao.deleteFavoriteAgents(agentEntity)
    }

    override fun getFavoriteAgent(): Flow<List<Agent>>{
        return favoriteAgentsDao.getFavoriteAgents().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }
    override fun checkUser(uuid: String): Flow<Int> = favoriteAgentsDao.checkUser(uuid)

    override fun getWeapons(): Flow<NetworkResult<List<Weapon>>> = flow {
        emit(NetworkResult.Loading)
        try{
            val response = agentsApiService.getWeapons()
            val responseBody = response.body()
            if(response.isSuccessful && responseBody != null){
                emit(NetworkResult.Success(DataMapper.mapResponseToListDomain(responseBody)))
            }else{
                emit(NetworkResult.Error(response.message()))
            }
        }catch (ex : Exception){
            emit(NetworkResult.Error(ex.message.toString()))
        }
    }


}
