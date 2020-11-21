package com.example.marvelapp.data.api

import okhttp3.Interceptor
import okhttp3.Response
import java.math.BigInteger
import java.security.MessageDigest

class NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val ts = System.currentTimeMillis().toString()
        val apikey = PUBLIC_KEY

        val httpUrl = request.url().newBuilder()
            .addQueryParameter(TS, ts)
            .addQueryParameter(API_KEY, apikey)
            .addQueryParameter(HASH, getHash(ts))
            .build()

        request = request.newBuilder().url(httpUrl).build()
        return chain.proceed(request)
    }

    fun getHash(ts: String) = "${ts}$PRIVATE_KEY$PUBLIC_KEY".md5

    private val String.md5: String
        get() {
            val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
            return bytes.joinToString("") {
                "%02x".format(it)
            }
        }

    companion object {
        private const val TS = "ts"
        private const val API_KEY = "apikey"
        private const val HASH = "hash"

        private const val PRIVATE_KEY = "3c7ccf34e0949bf24c23e8cbb268aba97eb8a12e"
        const val PUBLIC_KEY = "935fb8f1ee74995b79ddef2fc776e4e5"

        fun String.md5(): String {
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
        }
    }
}