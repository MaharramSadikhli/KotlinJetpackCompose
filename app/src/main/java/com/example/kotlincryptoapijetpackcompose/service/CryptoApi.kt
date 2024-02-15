package com.example.kotlincryptoapijetpackcompose.service

import com.example.kotlincryptoapijetpackcompose.model.CryptoModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CryptoApi {

    @GET("MaharramSadikhli/APIs/main/cryptocurrencyapi.json")
    fun getData() : Call<List<CryptoModel>>

}