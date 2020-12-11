package com.marvelapp06.marvelapp.data.api

interface IOnResult<T> {
    fun onSuccess(result: T)
    fun onFailure()
}