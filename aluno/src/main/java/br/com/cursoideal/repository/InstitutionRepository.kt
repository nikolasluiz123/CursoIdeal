package br.com.cursoideal.repository

import br.com.cursoideal.service.viacep.ViaCepWebClient
import br.com.cursoideal.transferobject.TOAddress
import com.google.firebase.firestore.FirebaseFirestore

class InstitutionRepository(
    private val firebaseFirestore: FirebaseFirestore,
    private val viaCepWebClient: ViaCepWebClient
) {

    suspend fun getTOAddresBy(cep: String): TOAddress? = viaCepWebClient.getTOAddresBy(cep)
}