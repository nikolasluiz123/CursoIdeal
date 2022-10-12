package br.com.cursoideal.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.cursoideal.databinding.MaintenanceCourseDialogBinding
import br.com.cursoideal.transferobject.TOCourse
import br.com.cursoideal.ui.dialog.base.AbstractFullScreenDialog
import br.com.cursoideal.ui.viewmodel.CourseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MaintenanceCourseDialog(private val toCourse: TOCourse = TOCourse()) : AbstractFullScreenDialog() {

    override val dialogTag: String = "MaintenanceCourseDialog"

    private var _binding: MaintenanceCourseDialogBinding? = null
    private val binding get() = _binding!!

    private val courseViewModel: CourseViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = MaintenanceCourseDialogBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        courseViewModel.toCourse.observe(viewLifecycleOwner) {
            binding.toCourse = it
        }

        binding.institutionsDialogToolbar.apply {
            setNavigationOnClickListener { dismiss() }
            setOnMenuItemClickListener {
                // Executar operações para salvar o curso
                // Avisar se foi falvo com sucesso ou não
                dismiss()
                true
            }
        }

        courseViewModel.toCourse.postValue(toCourse)
    }
}