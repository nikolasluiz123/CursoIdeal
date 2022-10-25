package br.com.cursoideal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.cursoideal.databinding.FragmentCoursesBinding
import br.com.cursoideal.databinding.FragmentHomeBinding
import br.com.cursoideal.ui.adapter.CoursesCompleteAdapter
import br.com.cursoideal.ui.fragment.base.AbstractSessionedFragment
import br.com.cursoideal.ui.viewmodel.CourseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoursesFragment : AbstractSessionedFragment() {

    private var _binding: FragmentCoursesBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { CoursesCompleteAdapter() }
    private val courseViewModel: CourseViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.coursesFragmentRecyclerview.adapter = adapter

        adapter.onItemClick = { toCourseComplete ->
            val idInstitution = toCourseComplete.toInstitution.id
            val idCourse = toCourseComplete.toCourse.id

            if (idInstitution != null && idCourse != null) {
                navController.navigate(CoursesFragmentDirections.actionCoursesFragmentToDetailsCourseFragment(idInstitution, idCourse))
            }
        }

        courseViewModel.findAll().observe(viewLifecycleOwner) { response -> response.data?.let(adapter::insert) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}