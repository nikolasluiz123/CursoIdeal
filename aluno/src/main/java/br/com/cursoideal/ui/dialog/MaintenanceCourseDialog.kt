package br.com.cursoideal.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import br.com.cursoideal.R
import br.com.cursoideal.databinding.MaintenanceCourseDialogBinding
import br.com.cursoideal.extensions.executeRequiredValidation
import br.com.cursoideal.repository.Response
import br.com.cursoideal.transferobject.TOCourse
import br.com.cursoideal.ui.dialog.base.AbstractFullScreenDialog
import br.com.cursoideal.ui.viewmodel.CourseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MaintenanceCourseDialog(
    private val toCourse: TOCourse = TOCourse(),
    private val institutionId: String?,
    private val callback: (response: Response<TOCourse>) -> Unit
) : AbstractFullScreenDialog() {

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

        courseViewModel.toCourse.postValue(toCourse)

        binding.institutionsDialogToolbar.apply {
            setNavigationOnClickListener { dismiss() }
            setOnMenuItemClickListener {
                val value = courseViewModel.toCourse.value

                if (value != null && institutionId != null && isValid()) {
                    courseViewModel.save(value, institutionId).observe(viewLifecycleOwner) {
                        callback(it)
                        dismiss()
                    }
                }
                true
            }
        }

        val items = listOf(
            getString(R.string.label_modality_item_presential),
            getString(R.string.label_modality_item_ead),
            getString(R.string.label_modality_item_hibrid)
        )
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
        binding.maintenanceCourseDialogInputModality.setAdapter(adapter)
    }

    private fun isValid(): Boolean {
        var valid = true

        valid = binding.maintenanceCourseDialogLayoutName.executeRequiredValidation() && valid
        valid = binding.maintenanceCourseDialogLayoutValue.executeRequiredValidation() && valid
        valid = binding.maintenanceCourseDialogLayoutModality.executeRequiredValidation() && valid
        valid = binding.maintenanceCourseDialogLayoutDuration.executeRequiredValidation() && valid

        return valid
    }
}