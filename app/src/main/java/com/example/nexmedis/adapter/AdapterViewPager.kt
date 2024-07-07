package com.example.nexmedis.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.nexmedis.fragment.FavoriteFragment
import com.example.nexmedis.fragment.HomeFragment

class AdapterViewPager(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> FavoriteFragment()
            else -> throw IllegalStateException("Invalid position")
        }
    }
}