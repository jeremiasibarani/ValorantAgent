package com.example.core.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AgentsEntity::class], version = 1)
abstract class FavoriteAgentDatabase : RoomDatabase() {

    abstract fun favoriteAgentsDao() : FavoriteAgentsDao

}