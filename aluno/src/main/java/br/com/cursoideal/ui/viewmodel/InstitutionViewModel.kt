package br.com.cursoideal.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.cursoideal.repository.InstitutionRepository
import br.com.cursoideal.transferobject.TOAddress
import br.com.cursoideal.transferobject.TOInstitution

class InstitutionViewModel(
    private val institutionRepository: InstitutionRepository,
    var toInstitution: MutableLiveData<TOInstitution> = MutableLiveData(TOInstitution())
) : ViewModel() {

    suspend fun getTOAddresBy(cep: String): TOAddress? = institutionRepository.getTOAddresBy(cep)
}