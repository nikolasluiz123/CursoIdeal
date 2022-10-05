package br.com.cursoideal.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.cursoideal.model.UserAuthentication
import br.com.cursoideal.transferobject.TOUser
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class FirebaseAuthenticationRepository(
    private val firebaseAuthentication: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
) {

    fun save(toUser: TOUser): LiveData<Resource<Boolean>> =
        MutableLiveData<Resource<Boolean>>().apply {
            firebaseAuthentication.createUserWithEmailAndPassword(
                toUser.email.value!!,
                toUser.password.value!!
            )
                .addOnCompleteListener { taskCreateUser ->

                    if (taskCreateUser.isSuccessful) {

                        taskCreateUser.result.user?.let { firebaseUser ->
                            toUser.byteArrayPhoto?.let { byteArray ->

                                val reference = firebaseStorage.reference.child("photos/users/${firebaseUser.uid}")
                                reference.putBytes(byteArray).addOnSuccessListener { uploadTask ->
                                    uploadTask.uploadSessionUri?.let { uri ->
                                        toUser.getUser()?.let { user ->
                                            val document = firebaseFirestore.collection("users").document()
                                            user.linkPhoto = uri.toString()
                                            document.set(user)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    value = Resource(
                        taskCreateUser.isSuccessful,
                        taskCreateUser.exception?.let(::getErrorMessageSave)
                    )
                }
        }

    private fun getErrorMessageSave(exception: Exception): String = when (exception) {
        is FirebaseAuthWeakPasswordException -> "A Senha precisa conter ao menos 6 dígitos"
        is FirebaseAuthInvalidCredentialsException -> "E-mail inválido"
        is FirebaseAuthUserCollisionException -> "E-mail já cadastrado"
        else -> "Erro desconhecido"
    }

    fun login(user: UserAuthentication): LiveData<Resource<Boolean>> =
        MutableLiveData<Resource<Boolean>>().apply {
            firebaseAuthentication.signInWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener { task ->
                    value = Resource(task.isSuccessful, task.exception?.let(::getErrorMessageLogin))
                }
        }

    private fun getErrorMessageLogin(exception: Exception?): String = when (exception) {
        is FirebaseAuthInvalidUserException,
        is FirebaseAuthInvalidCredentialsException -> "E-mail ou senha incorretos"
        else -> "Erro desconhecido"
    }

    fun logout() = firebaseAuthentication.signOut()

    fun isLogged() = firebaseAuthentication.currentUser != null

}