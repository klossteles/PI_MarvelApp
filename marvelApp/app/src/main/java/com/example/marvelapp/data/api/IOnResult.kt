package com.example.marvelapp.data.api

interface IOnResult<T> {
    fun onSuccess(result: T)
    fun onFailure()
}