package com.patelestate.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class PagerAdapter  // private final ArrayList<String> titulos = new ArrayList<String>();
// private int NUM_PAGES =0;
    (manager: FragmentManager?, num_pages: Int) : FragmentPagerAdapter(manager!!) {
    private val mFragments: ArrayList<Fragment> = ArrayList<Fragment>()
    fun addFragment(fragment: Fragment, title: String?) {
        mFragments.add(fragment)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        //return NUM_PAGES;
        return mFragments.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }
}