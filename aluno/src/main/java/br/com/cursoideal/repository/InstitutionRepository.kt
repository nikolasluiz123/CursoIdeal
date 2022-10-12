package br.com.cursoideal.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.cursoideal.model.Institution
import br.com.cursoideal.repository.enumerations.FirebaseCollections
import br.com.cursoideal.service.viacep.ViaCepWebClient
import br.com.cursoideal.transferobject.TOAddress
import br.com.cursoideal.transferobject.TOInstitution
import com.google.firebase.firestore.FirebaseFirestore
import java.util.stream.Collectors

class InstitutionRepository(
    private val firebaseFirestore: FirebaseFirestore,
    private val viaCepWebClient: ViaCepWebClient
) {

    fun save(institution: Institution): LiveData<Resource<Boolean>> = MutableLiveData<Resource<Boolean>>().apply {
        firebaseFirestore.collection(FirebaseCollections.INSTITUTIONS.value).document().set(institution).addOnCompleteListener { task ->
            value = Resource(task.isSuccessful, task.exception?.message)
        }
    }

    fun findAll(): LiveData<List<TOInstitution>> = MutableLiveData<List<TOInstitution>>().apply {
        val list: MutableList<Institution> = mutableListOf()

        firebaseFirestore.collection(FirebaseCollections.INSTITUTIONS.value).get().addOnSuccessListener { querySnapshot ->
            querySnapshot.documents.forEach { document ->
                document.toObject(Institution::class.java)?.let { institution ->
                    institution.id = document.id
                    list.add(institution)
                }
            }

            value = list.stream().map(::TOInstitution).collect(Collectors.toList())
        }
    }

    fun findById(id: String): LiveData<TOInstitution?> = MutableLiveData<TOInstitution>().apply {
        firebaseFirestore.collection(FirebaseCollections.INSTITUTIONS.value).document(id).get().addOnSuccessListener { document ->
            document.toObject(Institution::class.java)?.let { institution ->
                institution.id = document.id
                value = TOInstitution(institution)
            }
        }
    }

    suspend fun getTOAddresBy(cep: String): TOAddress? = viaCepWebClient.getTOAddresBy(cep)
}