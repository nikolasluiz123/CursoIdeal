package br.com.cursoideal.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import br.com.cursoideal.R
import br.com.cursoideal.databinding.InstitutionsDialogBinding
import br.com.cursoideal.transferobject.TOAddress
import br.com.cursoideal.transferobject.TOInstitution
import br.com.cursoideal.ui.adapter.InstitutionsAdapter
import br.com.cursoideal.ui.dialog.base.AbstractFullScreenDialog
import br.com.cursoideal.ui.viewmodel.InstitutionViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class InstitutionsDialog(private val callback: (toInstitution: TOInstitution) -> Unit) : AbstractFullScreenDialog() {

    override val dialogTag: String = "InstitutionsDialog"

    private var _binding: InstitutionsDialogBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { InstitutionsAdapter() }

    private val institutionViewModel: InstitutionViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = InstitutionsDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.institutionsDialogToolbar.apply {
            setNavigationOnClickListener { dismiss() }
        }

        binding.institutionsDialogRecyclerview.adapter = adapter

        adapter.onItemClick = { toInstitution ->
            callback(toInstitution)
            dismiss()
        }

        institutionViewModel.findAll().observe(viewLifecycleOwner) { institutions ->
            adapter.insert(institutions)
        }
    }
}