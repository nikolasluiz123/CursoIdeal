package br.com.cursoideal.service.viacep

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepService {

    @GET("{CEP}/json/")
    suspend fun getAddressBy(@Path("CEP") cep: String): Response<ViaCepAddress>
}