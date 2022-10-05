package br.com.cursoideal.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.cursoideal.model.UserAuthentication
import br.com.cursoideal.repository.FirebaseAuthenticationRepository
import br.com.cursoideal.repository.Resource
import br.com.cursoideal.transferobject.TOUser

class AuthenticationViewModel(private val firebaseAuthRepository: FirebaseAuthenticationRepository) :
    ViewModel() {

    fun save(toUser: TOUser): LiveData<Resource<Boolean>> = firebaseAuthRepository.save(toUser)

    fun login(user: UserAuthentication): LiveData<Resource<Boolean>> = firebaseAuthRepository.login(user)

    fun logout() = firebaseAuthRepository.logout()

    fun isNotLogged() = !firebaseAuthRepository.isLogged()
}