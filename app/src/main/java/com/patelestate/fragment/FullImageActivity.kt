package com.patelestate.fragment

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.patelestate.R
import com.patelestate.adapter.ImageAdapter
import com.zeuskartik.mediaslider.MediaSliderActivity

class FullImageActivity : MediaSliderActivity() {
    private var viewPager: ViewPager? = null
    private var btnClose: ImageView? = null
    private var count: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_image)
        Log.e("ArrayImages", arrSlideImages.size.toString())
        val position = intent.getIntExtra("posiiton", 0)
        viewPager = findViewById<ViewPager>(R.id.mViewPager)
        btnClose = findViewById<ImageView>(R.id.btnClose)
        count = findViewById<TextView>(R.id.count)
        val viewPagerAdapter = ImageAdapter(this@FullImageActivity, arrSlideImages!!)
        viewPager!!.adapter = viewPagerAdapter
        viewPager!!.currentItem = position
        val a =   viewPager!!.currentItem+1;
        count!!.setText(a.toString() + "/" + arrSlideImages.size.toString())


        btnClose!!.setOnClickListener {
            finish()
        }

        viewPager!!.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                positions: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                val a =   viewPager!!.currentItem+1;
                count!!.setText(a.toString() + "/" + arrSlideImages.size.toString())
            }

            override fun onPageSelected(positions: Int) {
                if (positions == position) {  // if you want the second page, for example
                    //Your code here

                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    companion object {
        var arrSlideImages: ArrayList<String> = ArrayList()
    }
}