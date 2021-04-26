package com.patelestate.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.patelestate.PatelEstate
import com.patelestate.R
import kotlin.math.roundToInt

class LocalityAdapter(
    private val mContext: Context,
    private val list: ArrayList<String>
) :

    RecyclerView.Adapter<LocalityAdapter.MovieViewHolder>() {
    var removedPosition: Int? = null
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
        RecyclerView.ViewHolder(inflater.inflate(R.layout.row_locality, parent, false)) {
        private var ivImage: ImageView? = null
        private var tvLocalityName: TextView? = null

        //
        init {
            ivImage = itemView.findViewById(R.id.ivLocalityImg)
            tvLocalityName = itemView.findViewById(R.id.tvLocalityName)
        }

        @SuppressLint("SetTextI18n")
        fun bind(locality: String) {
            val params: ViewGroup.LayoutParams = ivImage!!.layoutParams
            params.width = (PatelEstate.width * 0.35).roundToInt()
            params.height = (PatelEstate.width * 0.35).roundToInt()
            ivImage?.requestLayout()

            tvLocalityName?.text = locality
//            txtAmount?.text = mContext.resources.getString(R.string.rupees) + bill.Amount.toString()
//            relMain?.setOnClickListener {
////                listner.onPropertyClick()
//            }
        }
    }

    interface onCommercialPropertyClick {
        fun onPropertyClick()
    }
}