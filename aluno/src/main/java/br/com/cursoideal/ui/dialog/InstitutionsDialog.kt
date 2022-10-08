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

private const val TAG: String = "InstitutionsDialog"

class InstitutionsDialog(private val callback: (toInstitution: TOInstitution) -> Unit) : DialogFragment() {

    private var _binding: InstitutionsDialogBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { context?.let(::InstitutionsAdapter) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
    }

    override fun onStart() {
        super.onStart()

        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setLayout(width, height)
    }

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
            setTitle(R.string.label_title_institutions_dialog)
        }

        adapter?.let { adapter ->
            binding.institutionsDialogRecyclerview.adapter = adapter

            adapter.onItemClick = { toInstitution ->
                callback(toInstitution)
                dismiss()
            }

            // Código de Teste
            adapter.insert(
                listOf(
                    TOInstitution(
                        "Fundação Universidade Regional de Blumenau FURB",
                        TOAddress(
                            "89030-903",
                            "SC",
                            "Blumenau",
                            "Itoupava Seca",
                            "R. Antônio da Veiga"
                        )
                    ),
                    TOInstitution(
                        "UniSociesc - Blumenau",
                        TOAddress(
                            "89010-350",
                            "SC",
                            "Blumenau",
                            "Jardim Blumenau",
                            "R. Pandiá Calógeras"
                        )
                    ),
                    TOInstitution(
                        "Fundação Universidade Regional de Blumenau FURB",
                        TOAddress(
                            "89030-903",
                            "SC",
                            "Blumenau",
                            "Itoupava Seca",
                            "R. Antônio da Veiga"
                        )
                    ),
                    TOInstitution(
                        "UniSociesc - Blumenau",
                        TOAddress(
                            "89010-350",
                            "SC",
                            "Blumenau",
                            "Jardim Blumenau",
                            "R. Pandiá Calógeras"
                        )
                    ),
                    TOInstitution(
                        "Fundação Universidade Regional de Blumenau FURB",
                        TOAddress(
                            "89030-903",
                            "SC",
                            "Blumenau",
                            "Itoupava Seca",
                            "R. Antônio da Veiga"
                        )
                    ),
                    TOInstitution(
                        "UniSociesc - Blumenau",
                        TOAddress(
                            "89010-350",
                            "SC",
                            "Blumenau",
                            "Jardim Blumenau",
                            "R. Pandiá Calógeras"
                        )
                    ),
                    TOInstitution(
                        "Fundação Universidade Regional de Blumenau FURB",
                        TOAddress(
                            "89030-903",
                            "SC",
                            "Blumenau",
                            "Itoupava Seca",
                            "R. Antônio da Veiga"
                        )
                    ),
                    TOInstitution(
                        "UniSociesc - Blumenau",
                        TOAddress(
                            "89010-350",
                            "SC",
                            "Blumenau",
                            "Jardim Blumenau",
                            "R. Pandiá Calógeras"
                        )
                    )
                )
            )
        }
    }

    fun show(manager: FragmentManager) {
        super.show(manager, TAG)
    }

}