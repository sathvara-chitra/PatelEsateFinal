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
import com.patelestate.model.ResedentialListDataItem
import com.patelestate.model.Residential.GetResidentialListItem
import com.patelestate.model.Root
import kotlin.math.roundToInt

class ResidencialPropertiesAdapter(
    private val mContext: Context,
    private val list: ArrayList<GetResidentialListItem>,
    val listner: onResidentalPropertyClick
) :

    RecyclerView.Adapter<ResidencialPropertiesAdapter.MovieViewHolder>() {
    var removedPosition: Int? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val pendingDonation: GetResidentialListItem = list[position]
        holder.bind(pendingDonation)
    }

    override fun getItemCount(): Int = list.size
    inner class MovieViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.row_residential_list, parent, false)) {
        private var ivImage: ImageView? = null
        private var relMain: RelativeLayout? = null
        private var txtExclusive: TextView? = null
        private var txtType: TextView? = null
        private var txtRate: TextView? = null
        private var txtAddress: TextView? = null
        private var txtBedroom: TextView? = null
        private var txtBathroom: TextView? = null
        private var txtArea: TextView? = null

        //
        init {
            ivImage = itemView.findViewById(R.id.ivImage)
            relMain = itemView.findViewById(R.id.relMainLayout)
            txtExclusive = itemView.findViewById(R.id.txtExclusive)
            txtType = itemView.findViewById(R.id.txtType)
            txtRate = itemView.findViewById(R.id.txtRate)
            txtAddress = itemView.findViewById(R.id.txtAddress)
            txtBedroom = itemView.findViewById(R.id.txtBedroom)
            txtBathroom = itemView.findViewById(R.id.txtBathroom)
            txtArea = itemView.findViewById(R.id.txtArea)
        }

        @SuppressLint("SetTextI18n")
        fun bind(bill: GetResidentialListItem) {
            val params: ViewGroup.LayoutParams = ivImage!!.layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height = (PatelEstate.width * 0.35).roundToInt()
            ivImage?.requestLayout()
            Glide.with(mContext)
                .load(bill.photoPath)
                .placeholder(R.drawable.ic_logo_lg)
                .into(ivImage!!);

            txtType?.text = bill.transactionType
            txtBedroom?.text = bill.bedroomsTotal.toString()
            txtBathroom?.text = bill.bathroomTotal.toString()
            txtArea?.text = bill.totalFinishedArea
            txtAddress?.text = bill.address
            txtRate?.text = "$" + bill.price + ".00"
            relMain?.setOnClickListener {
                listner.onPropertyClick(bill.propertyUniqid)
            }
        }
    }

    interface onResidentalPropertyClick {
        fun onPropertyClick(propertyUniqid: String)
    }
}