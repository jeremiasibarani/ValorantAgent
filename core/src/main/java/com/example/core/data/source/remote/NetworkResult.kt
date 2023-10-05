package com.example.core.data.source.remote

sealed class NetworkResult<out R> private constructor(){
    data class Success<out T>(val data : T) : NetworkResult<T>()
    data class Error(val message : String) : NetworkResult<Nothing>()
    data object Loading : NetworkResult<Nothing>()
}