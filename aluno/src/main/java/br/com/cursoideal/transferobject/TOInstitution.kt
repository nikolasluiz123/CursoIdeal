package br.com.cursoideal.transferobject

import br.com.cursoideal.model.Institution

class TOInstitution(
    id: String? = null,
    var name: String = "",
    var toAddress: TOAddress = TOAddress(),
) : BaseTO(id) {

    constructor(institution: Institution) : this(
        institution.id,
        institution.name,
        TOAddress(institution.address)
    )

}