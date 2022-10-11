package br.com.cursoideal.model

import androidx.lifecycle.MutableLiveData
import br.com.cursoideal.transferobject.TOAddress

data class Address(
    var cep: String = "",
    var state: String = "",
    var city: String = "",
    var district: String = "",
    var publicPlace: String = "",
    var complement: String = ""
) {

}