package com.patelestate.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.patelestate.R
import com.zeuskartik.mediaslider.TouchImageView


class ImageAdapter internal constructor(context: Context,var arrImages: ArrayList<String>) : PagerAdapter() {
    var context: Context

    private var layoutInflater: LayoutInflater? = null
    override fun getCount(): Int {
        return arrImages.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        val itemView: View = layoutInflater!!.inflate(R.layout.pager_item, null)
        val imageView: TouchImageView = itemView.findViewById(R.id.imageView) as TouchImageView
        Glide.with(context)
            .load(arrImages.get(position))
            .into(imageView!!);
        container.addView(itemView)
        return itemView
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