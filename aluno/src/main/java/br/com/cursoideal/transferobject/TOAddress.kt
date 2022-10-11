package br.com.cursoideal.transferobject

data class TOAddress(
    var cep: String = "",
    var state: String = "",
    var city: String = "",
    var district: String = "",
    var publicPlace: String = "",
    var complement: String = ""
) {

    fun getCompleteAddress(): String {
        return "$publicPlace - $district, $city - $state"
    }

}