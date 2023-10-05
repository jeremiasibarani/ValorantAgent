package com.example.core.data.source.remote

import com.example.core.data.source.remote.response.AgentByIdResponse
import com.example.core.data.source.remote.response.AgentsResponse
import com.example.core.data.source.remote.response.WeaponsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AgentsApiService{
    @GET("agents")
    suspend fun getAgents() : Response<AgentsResponse>

    @GET("agents/{uuid}")
    suspend fun getAgentById(@Path("uuid") id : String) : Response<AgentByIdResponse>

    @GET("weapons")
    suspend fun getWeapons() : Response<WeaponsResponse>
}
