package com.patelestate.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.patelestate.R

class PropertyTypeAdapter(
    private val mContext: Context,
    private val list: ArrayList<String>,
    val listner: onPropertyTypeClick
) :


    RecyclerView.Adapter<PropertyTypeAdapter.MovieViewHolder>() {
    var removedPosition: Int? = null
    private var selectedItemPosition: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val pendingDonation: String = list[position]
        holder.bind(pendingDonation)
    }

    override fun getItemCount(): Int = list.size
    inner class MovieViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.row_propertytype, parent, false)) {
        private var txtName: TextView? = null
        private var ll_main: LinearLayout? = null

        //
        init {
            txtName = itemView.findViewById(R.id.txtName)
            ll_main = itemView.findViewById(R.id.ll_main)
        }

        @SuppressLint("SetTextI18n")
        fun bind(locality: String) {

            if (selectedItemPosition == position) {
                ll_main!!.setBackgroundResource(R.drawable.btn_light_yello)
            } else {
                ll_main!!.setBackgroundResource(R.drawable.rounded_grey_border)
            }
            txtName?.text = locality
            ll_main!!.setOnClickListener {
                selectedItemPosition = position
                if(selectedItemPosition == position)
                    ll_main!!.setBackgroundResource(R.drawable.btn_light_yello)
                else
                    ll_main!!.setBackgroundResource(R.drawable.rounded_grey_border)
                listner.onPropertyTypeClick(locality)
                notifyDataSetChanged()

            }
//            txtAmount?.text = mContext.resources.getString(R.string.rupees) + bill.Amount.toString()
//            relMain?.setOnClickListener {
////                listner.onPropertyClick()
//            }
        }
    }

    interface onPropertyTypeClick {
        fun onPropertyTypeClick(PropertyType: String)
    }
}