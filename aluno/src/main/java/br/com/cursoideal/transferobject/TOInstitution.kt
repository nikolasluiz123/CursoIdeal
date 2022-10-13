package br.com.cursoideal.transferobject

import br.com.cursoideal.model.Institution

class TOInstitution(
    id: String? = null,
    var name: String = "",
    var toAddress: TOAddress = TOAddress(),
) : BaseTO(id) {

    constructor(id: String, institution: Institution) : this(
        id,
        institution.name,
        TOAddress(address = institution.address)
    )

}