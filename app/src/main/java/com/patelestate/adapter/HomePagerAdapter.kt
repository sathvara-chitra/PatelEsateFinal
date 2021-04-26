package com.patelestate.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.patelestate.fragment.home.CommercialFragment
import com.patelestate.fragment.home.ResidentialFragment

class HomePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ResidentialFragment()
            }
            else -> {
                return CommercialFragment()
            }
        }
    }

    override fun getItemPosition(obj: Any): Int {
        return PagerAdapter.POSITION_NONE;
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Residential"
            else -> {
                return "Commercial"
            }
        }
    }
}