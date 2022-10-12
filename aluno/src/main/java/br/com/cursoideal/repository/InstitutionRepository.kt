package br.com.cursoideal.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.cursoideal.model.Institution
import br.com.cursoideal.service.viacep.ViaCepWebClient
import br.com.cursoideal.transferobject.TOAddress
import com.google.firebase.firestore.FirebaseFirestore

class InstitutionRepository(
    private val firebaseFirestore: FirebaseFirestore,
    private val viaCepWebClient: ViaCepWebClient
) {

    fun save(institution: Institution): LiveData<Resource<Boolean>> = MutableLiveData<Resource<Boolean>>().apply {
        firebaseFirestore.collection("institutions").document().set(institution).addOnCompleteListener { task ->
            value = Resource(task.isSuccessful, task.exception?.message)
        }
    }

    suspend fun getTOAddresBy(cep: String): TOAddress? = viaCepWebClient.getTOAddresBy(cep)
}