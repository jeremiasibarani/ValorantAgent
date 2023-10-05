package com.example.core.data.source.remote.response

data class AgentsResponse(
    val status : Int,
    val data : List<AgentResponse>
)

data class AgentResponse(
    val uuid : String,
    val displayName : String,
    val fullPortrait : String?,
    val fullPortraitV2: String?
)

data class AgentByIdResponse(
    val status : Int,
    val data : AgentByIdData
)

data class AgentByIdData(
    val uuid : String,
    val displayName : String,
    val fullPortrait : String?,
    val fullPortraitV2: String?,
    val role : AgentRole,
    val description : String
)

data class AgentRole(
    val displayName: String
)

data class WeaponsResponse(
    val status : Int,
    val data : List<WeaponData>
)

data class WeaponData(
    val uuid : String,
    val displayName: String,
    val displayIcon : String?
)