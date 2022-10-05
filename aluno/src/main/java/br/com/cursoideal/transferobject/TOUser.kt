package br.com.cursoideal.transferobject

import androidx.lifecycle.MutableLiveData
import br.com.cursoideal.model.User
import br.com.cursoideal.model.UserAuthentication

class TOUser (private var userAuthentication: UserAuthentication = UserAuthentication(),
              private var user: User = User(),
              val email: MutableLiveData<String> = MutableLiveData<String>().also { it.value = userAuthentication.email},
              val password: MutableLiveData<String> = MutableLiveData<String>().also { it.value = userAuthentication.password},
              val name: MutableLiveData<String> = MutableLiveData<String>().also { it.value = user.name},
              var linkPhoto: String? = null,
              var byteArrayPhoto: ByteArray? = null
) {

    fun getUserAuthentication(): UserAuthentication? {
        return this.userAuthentication.copy(
            email = email.value ?: return null,
            password = password.value ?: return null
        )
    }

    fun getUser(): User? {
        return this.user.copy(
            name = name.value ?: return null
        )
    }
}