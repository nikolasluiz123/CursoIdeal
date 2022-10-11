package br.com.cursoideal.service.viacep

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ViaCepRetrofitInitializer {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://viacep.com.br/ws/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val viaCepService: ViaCepService = retrofit.create(ViaCepService::class.java)
}