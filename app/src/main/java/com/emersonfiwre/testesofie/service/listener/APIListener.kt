package com.emersonfiwre.testesofie.service.listener

interface APIListener<T> {
    fun onSuccess(result: T, statusCode: Int)
    fun onFailure(message: String)
}