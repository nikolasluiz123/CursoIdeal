package br.com.cursoideal.model

import br.com.cursoideal.transferobject.TOAddress

class Address(
    var cep: String = "",
    var state: String = "",
    var city: String = "",
    var district: String = "",
    var publicPlace: String = "",
    var complement: String = ""
) {
    constructor(toAddress: TOAddress) : this(
        toAddress.cep,
        toAddress.state,
        toAddress.city,
        toAddress.district,
        toAddress.publicPlace,
        toAddress.complement
    )
}