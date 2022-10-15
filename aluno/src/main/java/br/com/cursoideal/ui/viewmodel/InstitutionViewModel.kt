package br.com.cursoideal.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.cursoideal.repository.InstitutionRepository
import br.com.cursoideal.repository.Response
import br.com.cursoideal.repository.ResponseVoid
import br.com.cursoideal.transferobject.TOAddress
import br.com.cursoideal.transferobject.TOInstitution

class InstitutionViewModel(
    private val institutionRepository: InstitutionRepository,
    var toInstitution: MutableLiveData<TOInstitution> = MutableLiveData(TOInstitution())
) : ViewModel() {

    fun save(toInstitution: TOInstitution): LiveData<ResponseVoid> = institutionRepository.save(toInstitution)

    fun findAll(): LiveData<Response<List<TOInstitution>>> = institutionRepository.findAll()

    fun findById(id: String): LiveData<Response<TOInstitution>> = institutionRepository.findById(id)

    fun delete(id: String): LiveData<ResponseVoid> = institutionRepository.delete(id)

    suspend fun getTOAddresBy(cep: String): TOAddress? = institutionRepository.getTOAddresBy(cep)
}