package br.com.cursoideal.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import br.com.cursoideal.R
import br.com.cursoideal.databinding.FragmentCourseTabBinding
import br.com.cursoideal.ui.adapter.CoursesAdapter
import br.com.cursoideal.ui.dialog.MaintenanceCourseDialog
import br.com.cursoideal.ui.fragment.base.AbstractSessionedFragment
import br.com.cursoideal.ui.viewmodel.CourseViewModel
import br.com.cursoideal.ui.viewmodel.InstitutionViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CourseTabFragment : AbstractSessionedFragment() {

    private var _binding: FragmentCourseTabBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { CoursesAdapter() }

    private val institutionId: String? by lazy {
        val institutionViewModel: InstitutionViewModel by sharedViewModel()
        institutionViewModel.toInstitution.value?.id
    }

    private val courseViewModel: CourseViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCourseTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureMenu()

        binding.courseFragmentRecyclerview.adapter = adapter

        adapter.onItemClick = { toCourse ->
            activity?.let { activity ->
                MaintenanceCourseDialog(
                    toCourse = toCourse,
                    institutionId = institutionId
                ) { response ->

                    if (response.success) {
                        response.data?.let(adapter::update)
                    }

                }.show(activity.supportFragmentManager)
            }
        }

        adapter.onRemoveItemClick = { courseId, position ->
            var succcess = false

            institutionId?.let { institutionId ->
                courseViewModel.delete(institutionId, courseId).observe(viewLifecycleOwner) { response ->
                    succcess = response.success

                    if (succcess) {
                        adapter.delete(position)
                    }
                }
            }

            succcess
        }

        institutionId?.let { id ->
            courseViewModel.findBy(id).observe(viewLifecycleOwner) { response ->
                response.data?.let(adapter::insert)
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.context_menu_course_item_delete -> onRemoveCourse()
        }

        return super.onContextItemSelected(item)
    }

    private fun onRemoveCourse() {

    }

    private fun configureMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_courses_fragment, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_item_add_course -> onAddCourse()
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun onAddCourse(): Boolean {
        activity?.let { activity ->
            MaintenanceCourseDialog(institutionId = institutionId) { response ->

                if (response.success) {
                    response.data?.let(adapter::insert)
                }

            }.show(activity.supportFragmentManager)
        }

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}