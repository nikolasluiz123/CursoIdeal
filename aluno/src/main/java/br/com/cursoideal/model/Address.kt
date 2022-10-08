package br.com.cursoideal.model

class Address(
    private val cep: String,
    private val state: String,
    private val city: String,
    private val district: String,
    private val publicPlace: String,
    private val complement: String = ""
)