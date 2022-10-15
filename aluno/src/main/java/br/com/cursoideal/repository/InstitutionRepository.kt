package br.com.cursoideal.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.cursoideal.model.Institution
import br.com.cursoideal.repository.enumerations.FirebaseCollections
import br.com.cursoideal.service.viacep.ViaCepWebClient
import br.com.cursoideal.transferobject.TOAddress
import br.com.cursoideal.transferobject.TOInstitution
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class InstitutionRepository(
    private val firebaseFirestore: FirebaseFirestore,
    private val viaCepWebClient: ViaCepWebClient
) {

    fun save(toInstitution: TOInstitution): LiveData<ResponseVoid> = MutableLiveData<ResponseVoid>().apply {
        val institutionCollection = firebaseFirestore.collection(FirebaseCollections.INSTITUTIONS.value)
        val document = toInstitution.id?.let(institutionCollection::document) ?: institutionCollection.document()

        document.set(Institution(toInstitution)).addOnCompleteListener { task ->
            value = ResponseVoid(success = task.isSuccessful, error = task.exception?.message)
        }
    }

    fun findAll(): LiveData<Response<List<TOInstitution>>> = MutableLiveData<Response<List<TOInstitution>>>().apply {
        val list: MutableList<TOInstitution> = mutableListOf()

        firebaseFirestore.collection(FirebaseCollections.INSTITUTIONS.value).get().addOnSuccessListener { querySnapshot ->

            querySnapshot.documents.forEach { document ->
                document.toObject<Institution>()?.let { institution ->
                    list.add(TOInstitution(document.id, institution))
                }
            }
            value = Response(true, list)

        }.addOnFailureListener { value = Response(false, error = it.message) }
    }

    fun findById(id: String): LiveData<Response<TOInstitution>> = MutableLiveData<Response<TOInstitution>>().apply {
        firebaseFirestore.collection(FirebaseCollections.INSTITUTIONS.value).document(id).get().addOnSuccessListener { document ->
            document.toObject(Institution::class.java)?.let { institution ->
                value = Response(true, TOInstitution(document.id, institution))
            }
        }.addOnFailureListener { value = Response(false, error = it.message) }
    }

    /**
     * Método que utiliza uma implementação não recomendada em dispositivos móveis.
     * Essa implementação deleta os cursos da instituição que foi deletada, isso deveria
     * ser feito via CloudFunction, porém, este é um recursos pago.
     *
     * https://stackoverflow.com/questions/52142186/how-to-delete-firestore-collection-from-android
     */
    fun delete(id: String): LiveData<ResponseVoid> = MutableLiveData<ResponseVoid>().apply {
        val document = firebaseFirestore.collection(FirebaseCollections.INSTITUTIONS.value).document(id)
        var deleted = 0

        document.delete().addOnCompleteListener { task ->
            ResponseVoid(task.isSuccessful, task.exception?.message)

            document.collection(FirebaseCollections.COURSES.value).limit(10).get().addOnCompleteListener { querySnapshot ->
                querySnapshot.result.forEach { document ->
                    document.reference.delete()
                    deleted++
                }

                if (deleted >= 10) {
                    delete(id)
                }
            }
        }
    }

    suspend fun getTOAddresBy(cep: String): TOAddress? = viaCepWebClient.getTOAddresBy(cep)
}