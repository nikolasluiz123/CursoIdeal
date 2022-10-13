package br.com.cursoideal.transferobject

import br.com.cursoideal.model.Address

class TOAddress(
    id: String? = null,
    var cep: String = "",
    var state: String = "",
    var city: String = "",
    var district: String = "",
    var publicPlace: String = "",
    var complement: String = ""
) : BaseTO(id) {

    constructor(id: String? = null, address: Address) : this(
        id,
        address.cep,
        address.state,
        address.city,
        address.district,
        address.publicPlace,
        address.complement
    )

    fun getCompleteAddress(): String {
        return "$publicPlace - $district, $city - $state"
    }

}