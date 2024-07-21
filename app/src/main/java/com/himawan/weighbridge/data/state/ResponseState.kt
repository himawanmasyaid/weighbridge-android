package com.himawan.weighbridge.data.state

sealed class ResponseState<T> {
    data class Success<T>(var data: T) : ResponseState<T>()
    data class Failed<T>(var message: String? = null): ResponseState<T>()
}