package br.com.cursoideal.model

class Institution(
    id: String? = null,
    val name: String = "",
    val address: Address = Address()
) : BaseModel(id)