package br.com.cursoideal.service.viacep

import br.com.cursoideal.transferobject.TOAddress

class ViaCepWebClient(private val service: ViaCepService) {

    suspend fun getTOAddresBy(cep: String): TOAddress? {
        val viaCepAddressResponse = service.getAddressBy(cep)
        return viaCepAddressResponse.body()?.getTOAddress()
    }
}