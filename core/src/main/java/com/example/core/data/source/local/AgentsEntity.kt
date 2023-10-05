package com.example.core.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_valorant_agents")
data class AgentsEntity(
    @PrimaryKey
    val uuid : String,
    val displayName : String,
    val imgUrl : String
)