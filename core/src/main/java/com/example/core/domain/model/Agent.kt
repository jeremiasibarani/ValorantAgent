package com.example.core.domain.model


data class Agent(
    val uuid : String,
    val displayName : String,
    val imgUrl : String
)

data class DetailAgent(
    val uuid : String,
    val displayName : String,
    val imgUrl: String,
    val role : String,
    val description : String
)

data class Weapon(
    val uuid : String,
    val displayName: String,
    val imgUrl: String
)
