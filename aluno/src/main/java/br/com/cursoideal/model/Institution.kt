package br.com.cursoideal.model

import br.com.cursoideal.transferobject.TOInstitution

data class Institution(
    val name: String = "",
    val address: Address = Address()
) {
    constructor(toInstitution: TOInstitution) : this(
        toInstitution.name,
        Address(toInstitution.toAddress)
    )
}