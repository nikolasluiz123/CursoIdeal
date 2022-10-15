package br.com.cursoideal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.cursoideal.databinding.FragmentCourseInfosTabBinding
import br.com.cursoideal.databinding.FragmentCoursesBinding
import br.com.cursoideal.databinding.FragmentHomeBinding
import br.com.cursoideal.ui.fragment.base.AbstractSessionedFragment

class CourseInfosTabFragment : AbstractSessionedFragment() {

    private var _binding: FragmentCourseInfosTabBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCourseInfosTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}