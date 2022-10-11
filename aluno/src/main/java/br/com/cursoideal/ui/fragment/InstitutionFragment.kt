package br.com.cursoideal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import br.com.cursoideal.databinding.FragmentInstitutionBinding
import br.com.cursoideal.transferobject.TOInstitution
import br.com.cursoideal.ui.dialog.InstitutionsDialog
import br.com.cursoideal.ui.fragment.base.AbstractSessionedFragment
import br.com.cursoideal.ui.viewmodel.InstitutionViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "InstitutionFragment"

class InstitutionFragment : AbstractSessionedFragment() {

    private var _binding: FragmentInstitutionBinding? = null
    private val binding get() = _binding!!

    private val institutionViewModel: InstitutionViewModel by viewModel()
    private val toInstitution by lazy { TOInstitution() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInstitutionBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.toInstitution = toInstitution

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        institutionViewModel.toInstitution.observe(viewLifecycleOwner) {
            binding.toInstitution = it
        }

        binding.institutionLayoutName.setEndIconOnClickListener {
            activity?.let { activity ->
                InstitutionsDialog { toInstitution ->
                    institutionViewModel.toInstitution.postValue(toInstitution)
                }.show(activity.supportFragmentManager)
            }
        }

        binding.institutionLayoutCep.setEndIconOnClickListener {
            lifecycleScope.launch {
                val cep = binding.institutionInputCep.text.toString()
                institutionViewModel.getTOAddresBy(cep)?.let { toAddress ->
                    if (institutionViewModel.toInstitution.value?.toAddress?.cep != toAddress.cep) {
                        val newInstitution = TOInstitution()
                        newInstitution.toAddress = toAddress
                        institutionViewModel.toInstitution.postValue(newInstitution)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}