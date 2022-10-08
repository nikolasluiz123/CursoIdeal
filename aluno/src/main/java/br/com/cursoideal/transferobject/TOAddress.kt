package br.com.cursoideal.transferobject

class TOAddress(
    private val cep: String,
    private val state: String,
    private val city: String,
    private val district: String,
    private val publicPlace: String,
    private val complement: String = ""
) {

    fun getCompleteAddress(): String {
        return "$publicPlace - $district, $city - $state"
    }
}