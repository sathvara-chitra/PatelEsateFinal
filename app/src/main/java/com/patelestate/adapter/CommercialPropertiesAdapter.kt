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
import com.patelestate.model.GetCommercialLIstItem
import kotlin.math.roundToInt

class CommercialPropertiesAdapter(
    private val mContext: Context,
    private val list: ArrayList<GetCommercialLIstItem>,
    val listner: onCommercialPropertyClick
) :
    RecyclerView.Adapter<CommercialPropertiesAdapter.MovieViewHolder>() {
    var removedPosition: Int? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val pendingDonation: GetCommercialLIstItem = list[position]
        holder.bind(pendingDonation)
    }

    override fun getItemCount(): Int = list.size
    inner class MovieViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.row_commercial_list, parent, false)) {
        private var ivImage: ImageView? = null
        private var txtExclusive: TextView? = null
        private var txtType: TextView? = null
        private var txtRate: TextView? = null
        private var txtAddress: TextView? = null
        private var relMain: RelativeLayout? = null

        //
        init {
            ivImage = itemView.findViewById(R.id.ivImage)
            relMain = itemView.findViewById(R.id.relMain)
            txtExclusive = itemView.findViewById(R.id.txtExclusive)
            txtType = itemView.findViewById(R.id.txtType)
            txtRate = itemView.findViewById(R.id.txtRate)
            txtAddress = itemView.findViewById(R.id.txtAddress)
        }

        @SuppressLint("SetTextI18n")
        fun bind(bill: GetCommercialLIstItem) {
            val params: ViewGroup.LayoutParams = ivImage!!.layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height = (PatelEstate.width * 0.35).roundToInt()
            ivImage?.requestLayout()
            Log.e("ImagePath", bill.photoPath)
            Glide.with(mContext)
                .load(bill.photoPath)
                .placeholder(R.drawable.ic_logo_lg)
                .into(ivImage!!);

            txtType?.text = bill.transactionType
            txtAddress?.text = bill.address
            txtRate?.text = "$"+bill.askingPrice+".00"
//            txtAmount?.text = mContext.resources.getString(R.string.rupees) + bill.Amount.toString()
            relMain?.setOnClickListener {
                listner.onPropertyClick(bill.propertyUniqid)
            }
        }
    }

    interface onCommercialPropertyClick {
        fun onPropertyClick(propertyUniqid: String)
    }
}