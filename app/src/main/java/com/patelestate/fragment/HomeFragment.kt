package com.patelestate.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.patelestate.R
import com.patelestate.adapter.HomePagerAdapter
import com.patelestate.utils.view.NonSwipeableViewPager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var viewpager_main: NonSwipeableViewPager? = null
    private var tabs_main: TabLayout? = null
    private var fragmentAdapter: HomePagerAdapter? = null
    private var btnToggle: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        init(root)
        return root
    }

    private fun init(root: View?) {
        btnToggle = root!!.findViewById(R.id.btnToggle)
        viewpager_main = root!!.findViewById(R.id.viewpager_main)
        tabs_main = root.findViewById(R.id.tabs_main)
        fragmentAdapter = HomePagerAdapter(activity!!.supportFragmentManager)
        viewpager_main!!.adapter = fragmentAdapter
        tabs_main!!.setupWithViewPager(viewpager_main)
        tabs_main!!.getTabAt(0)!!.setIcon(R.drawable.ic_residential)
        tabs_main!!.getTabAt(1)!!.setIcon(R.drawable.ic_commercial)
        viewpager_main!!.setOffscreenPageLimit(0);
        btnToggle!!.setOnClickListener {
            if (btnToggle!!.tag == "0") {
                btnToggle?.tag = "1"
                btnToggle?.text = "Map"
            } else {
                btnToggle?.tag = "0"
                btnToggle?.text = "List"
            }
        }
    }


}