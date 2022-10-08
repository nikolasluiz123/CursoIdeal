package br.com.cursoideal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.cursoideal.databinding.FragmentRatingBinding
import br.com.cursoideal.ui.fragment.base.AbstractSessionedFragment
import br.com.cursoideal.ui.viewmodel.ComponentsViewControll

class RatingFragment : AbstractSessionedFragment() {

    private var _binding: FragmentRatingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRatingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}