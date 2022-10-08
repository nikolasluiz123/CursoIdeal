package br.com.cursoideal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.cursoideal.databinding.FragmentInstitutionBinding
import br.com.cursoideal.ui.dialog.InstitutionsDialog
import br.com.cursoideal.ui.fragment.base.AbstractSessionedFragment

class InstitutionFragment : AbstractSessionedFragment() {

    private var _binding: FragmentInstitutionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInstitutionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.institutionLayoutName.setEndIconOnClickListener {
            activity?.let { activity ->
                InstitutionsDialog { toInstitution ->
                    // atualiza o toInstitution do bindind para definir as informações
                    // nos campos da tela
                }.show(activity.supportFragmentManager)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}