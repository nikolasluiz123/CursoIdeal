package br.com.cursoideal.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(private val fragments: List<Fragment>,
                       rootFragment: Fragment) : FragmentStateAdapter(rootFragment) {

    override fun getItemCount(): Int = 3
    override fun createFragment(position: Int) = fragments[position]
}