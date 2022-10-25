package br.com.cursoideal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import br.com.cursoideal.R
import br.com.cursoideal.databinding.FragmentDetailsCourseBinding
import br.com.cursoideal.ui.adapter.ViewPagerAdapter
import br.com.cursoideal.ui.fragment.base.AbstractSessionedFragment
import com.google.android.material.tabs.TabLayoutMediator

class DetailsCourseFragment : AbstractSessionedFragment() {

    private var _binding: FragmentDetailsCourseBinding? = null
    private val binding get() = _binding!!

    private val args: DetailsCourseFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailsCourseBinding.inflate(inflater, container, false)
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
            binding.detailsCourseTabLayout,
            binding.detailsCourseViewPager
        ) { tab, position ->

            when (position) {
                0 -> tab.text = getString(R.string.label_title_tab_general)
                1 -> tab.text = getString(R.string.label_title_tab_rating)
            }

        }.attach()
    }

    private fun configureViewPageAdapter() {
        binding.detailsCourseViewPager.adapter = ViewPagerAdapter(
            listOf(
                CourseInfosTabFragment(args),
                CouseRatingTabFragment(args)
            ), this
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}