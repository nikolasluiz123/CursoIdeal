package br.com.cursoideal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.cursoideal.databinding.FragmentCourseInfosTabBinding
import br.com.cursoideal.ui.fragment.base.AbstractSessionedFragment
import br.com.cursoideal.ui.viewmodel.CourseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CourseInfosTabFragment(private val args: DetailsCourseFragmentArgs) : AbstractSessionedFragment() {

    private var _binding: FragmentCourseInfosTabBinding? = null
    private val binding get() = _binding!!

    private val courseViewModel: CourseViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCourseInfosTabBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        courseViewModel.findBy(args.idInstitution, args.idCourse).observe(viewLifecycleOwner) { response ->
            courseViewModel.toCourseComplete.postValue(response.data)
        }

        courseViewModel.toCourseComplete.observe(viewLifecycleOwner) {
            binding.toCourseComplete = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}