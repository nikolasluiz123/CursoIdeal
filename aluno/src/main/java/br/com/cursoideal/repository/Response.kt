package br.com.cursoideal.repository

class Response<T>(val success: Boolean, val data: T? = null, val error: String? = null)

class ResponseVoid(val success: Boolean, val error: String? = null)