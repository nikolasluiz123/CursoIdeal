package br.com.cursoideal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.cursoideal.databinding.FragmentListaCursosBinding
import br.com.cursoideal.ui.fragment.base.AbstractSessionedFragment

class ListaCursosFragment : AbstractSessionedFragment() {

    private var _binding: FragmentListaCursosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListaCursosBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}