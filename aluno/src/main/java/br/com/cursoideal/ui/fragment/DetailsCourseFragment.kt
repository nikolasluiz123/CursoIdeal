package br.com.cursoideal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.cursoideal.databinding.FragmentCoursesBinding
import br.com.cursoideal.databinding.FragmentDetailsCourseBinding
import br.com.cursoideal.ui.fragment.base.AbstractSessionedFragment

class DetailsCourseFragment : AbstractSessionedFragment() {

    private var _binding: FragmentDetailsCourseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailsCourseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}