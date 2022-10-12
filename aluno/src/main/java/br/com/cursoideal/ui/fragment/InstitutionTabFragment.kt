package br.com.cursoideal.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import br.com.cursoideal.R
import br.com.cursoideal.databinding.FragmentInstitutionTabBinding
import br.com.cursoideal.extensions.executeRequiredValidation
import br.com.cursoideal.extensions.showSnackBar
import br.com.cursoideal.transferobject.TOInstitution
import br.com.cursoideal.ui.dialog.InstitutionsDialog
import br.com.cursoideal.ui.fragment.base.AbstractSessionedFragment
import br.com.cursoideal.ui.viewmodel.InstitutionViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class InstitutionTabFragment(private val args: MaintenanceInstitutionFragmentArgs) : AbstractSessionedFragment() {

    private var _binding: FragmentInstitutionTabBinding? = null
    private val binding get() = _binding!!

    private val institutionViewModel: InstitutionViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInstitutionTabBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureMenu()

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

        args.institutionId?.let { id ->
            institutionViewModel.findById(id).observe(viewLifecycleOwner) { toInstitution ->
                institutionViewModel.toInstitution.postValue(toInstitution)
            }
        }
    }

    private fun configureMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_institution_fragment, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_item_institution_fragment_save -> onSave()
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun onSave(): Boolean {
        if (isValid()) {
            institutionViewModel.toInstitution.value?.let(institutionViewModel::save)
            view?.showSnackBar(getString(R.string.message_save_institution))
        }

        return true
    }

    private fun isValid(): Boolean {
        var valid = true

        valid = binding.institutionLayoutName.executeRequiredValidation() && valid
        valid = binding.institutionLayoutCep.executeRequiredValidation() && valid
        valid = binding.institutionLayoutState.executeRequiredValidation() && valid
        valid = binding.institutionLayoutCity.executeRequiredValidation() && valid
        valid = binding.institutionLayoutDistrict.executeRequiredValidation() && valid
        valid = binding.institutionLayoutPublicPlace.executeRequiredValidation() && valid

        return valid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}