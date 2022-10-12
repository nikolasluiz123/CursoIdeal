package br.com.cursoideal.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import br.com.cursoideal.R
import br.com.cursoideal.databinding.FragmentCourseTabBinding
import br.com.cursoideal.transferobject.TOCourse
import br.com.cursoideal.ui.adapter.CoursesAdapter
import br.com.cursoideal.ui.dialog.MaintenanceCourseDialog
import br.com.cursoideal.ui.fragment.base.AbstractSessionedFragment

class CourseTabFragment(private val args: MaintenanceInstitutionFragmentArgs) : AbstractSessionedFragment() {

    private var _binding: FragmentCourseTabBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { CoursesAdapter() }

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
                MaintenanceCourseDialog(toCourse).show(activity.supportFragmentManager)
            }
        }
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
            MaintenanceCourseDialog().show(activity.supportFragmentManager)
        }

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}