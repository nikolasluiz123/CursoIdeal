package br.com.cursoideal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import br.com.cursoideal.R
import br.com.cursoideal.databinding.FragmentMaintenanceCourceBinding
import br.com.cursoideal.ui.adapter.ViewPagerAdapter
import br.com.cursoideal.ui.fragment.base.AbstractSessionedFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MaintenanceCourceFragment : AbstractSessionedFragment() {

    private var _binding: FragmentMaintenanceCourceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMaintenanceCourceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureTabs()
    }

    private fun configureTabs() {
        configureViewPageAdapter()
        configureMediator()
    }

    private fun configureMediator() {
        TabLayoutMediator(
            binding.maintenanceCourseTabLayout,
            binding.maintenanceCourseViewPager
        ) { tab, position ->

            when (position) {
                0 -> tab.text = getString(R.string.label_title_tab_instituition)
                1 -> tab.text = getString(R.string.label_title_tab_course)
            }

        }.attach()
    }

    private fun configureViewPageAdapter() {
        binding.maintenanceCourseViewPager.adapter = ViewPagerAdapter(
            listOf(
                InstitutionFragment(),
                CourseFragment()
            ), this
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}