package br.com.cursoideal.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.cursoideal.model.Address
import br.com.cursoideal.model.Institution
import br.com.cursoideal.repository.InstitutionRepository
import br.com.cursoideal.repository.Resource
import br.com.cursoideal.transferobject.TOAddress
import br.com.cursoideal.transferobject.TOInstitution

class InstitutionViewModel(
    private val institutionRepository: InstitutionRepository,
    var toInstitution: MutableLiveData<TOInstitution> = MutableLiveData(TOInstitution())
) : ViewModel() {

    fun save(toInstitution: TOInstitution): LiveData<Resource<Boolean>> {
        val institution = Institution(
            toInstitution.id,
            toInstitution.name,
            Address(
                toInstitution.toAddress.id,
                toInstitution.toAddress.cep,
                toInstitution.toAddress.state,
                toInstitution.toAddress.city,
                toInstitution.toAddress.district,
                toInstitution.toAddress.publicPlace,
                toInstitution.toAddress.complement
            )
        )

        return institutionRepository.save(institution)
    }

    fun findAll(): LiveData<List<TOInstitution>> = institutionRepository.findAll()

    fun findById(id: String): LiveData<TOInstitution?> = institutionRepository.findById(id)

    suspend fun getTOAddresBy(cep: String): TOAddress? = institutionRepository.getTOAddresBy(cep)
}