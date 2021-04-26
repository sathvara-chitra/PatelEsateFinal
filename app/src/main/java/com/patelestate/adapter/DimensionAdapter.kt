package com.patelestate.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.patelestate.PatelEstate
import com.patelestate.R
import com.patelestate.model.DimensionModel
import com.patelestate.model.GetCommercialLIstItem
import kotlin.math.roundToInt

class DimensionAdapter(
    private val mContext: Context,
    private val list: ArrayList<DimensionModel>

) :

    RecyclerView.Adapter<DimensionAdapter.MovieViewHolder>() {
    var removedPosition: Int? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val pendingDonation: DimensionModel = list[position]
        holder.bind(pendingDonation)
    }

    override fun getItemCount(): Int = list.size
    inner class MovieViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.row_dimension, parent, false)) {
        private var type: TextView? = null
        private var dimension: TextView? = null


        //
        init {
            type = itemView.findViewById(R.id.type)
            dimension = itemView.findViewById(R.id.dimension)

        }

        @SuppressLint("SetTextI18n")
        fun bind(bill: DimensionModel) {

            type?.text = bill.type
            dimension?.text = bill.dimension

        }
    }


}