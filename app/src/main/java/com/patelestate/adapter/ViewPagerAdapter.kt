package com.patelestate.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.patelestate.R
import com.patelestate.fragment.FullImageActivity
import com.patelestate.fragment.FullImageActivity.Companion.arrSlideImages


class ViewPagerAdapter(context: Context, var arrImages: ArrayList<String>) : PagerAdapter() {
    private val context: Context
    private var layoutInflater: LayoutInflater? = null
    private val images =
        arrayOf<Int>(
            R.drawable.ic_building,
            R.drawable.ic_building,
            R.drawable.ic_building,
            R.drawable.ic_building,
            R.drawable.ic_building,
            R.drawable.ic_building
        )

    override fun getCount(): Int {
        return arrImages.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        val view: View = layoutInflater!!.inflate(R.layout.row_slide_image, null)
        val imageView: ImageView = view.findViewById(R.id.imageView) as ImageView

        Log.d("ImagesPath",arrImages.get(position))

        Glide.with(context)
            .load(arrImages.get(position))
            .into(imageView!!);
        val vp = container as ViewPager
        vp.addView(view, 0)
        view.setOnClickListener {
            arrSlideImages = ArrayList()
            arrSlideImages = arrImages
            val intent = Intent(context, FullImageActivity::class.java)
            intent.putExtra("posiiton",position)
            context.startActivity(intent)
        }
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val view: View = `object` as View
        vp.removeView(view)
    }

    init {
        this.context = context
    }
}