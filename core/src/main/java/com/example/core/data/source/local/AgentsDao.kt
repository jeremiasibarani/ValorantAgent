package com.example.core.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteAgentsDao {
    @Query("SELECT * FROM fav_valorant_agents")
    fun getFavoriteAgents() : Flow<List<AgentsEntity>>

    @Insert
    fun insertFavoriteAgents(agents : AgentsEntity)

    @Delete
    fun deleteFavoriteAgents(agents: AgentsEntity)

    @Query("SELECT count(*) FROM fav_valorant_agents WHERE uuid = :agentId")
    fun checkUser(agentId : String) : Flow<Int>
}