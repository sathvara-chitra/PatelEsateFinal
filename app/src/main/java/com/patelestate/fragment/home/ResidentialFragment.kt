package com.patelestate.fragment.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crownfantancy.ui.Retrofit.RetrofitClient
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener
import com.crystal.crystalrangeseekbar.widgets.BubbleThumbRangeSeekbar
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonArray
import com.patelestate.Loader
import com.patelestate.R
import com.patelestate.adapter.LocalityAdapter
import com.patelestate.adapter.ResidencialPropertiesAdapter
import com.patelestate.fragment.home.residential.ResidentalDetailsActivity
import com.patelestate.model.ResedentialListData
import com.patelestate.model.ResedentialListDataItem
import com.patelestate.model.Residential.GetResidentialList
import com.patelestate.model.Residential.GetResidentialListItem
import com.patelestate.model.Root
import com.patelestate.utils.SharedPrefsConstant
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResidentialFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResidentialFragment : Fragment(),ResidencialPropertiesAdapter.onResidentalPropertyClick,
    View.OnClickListener {
    var call: Call<JsonArray>? = null
    private var url: String? = null
    private var dialog: android.app.AlertDialog? = null
    private var recyclerViewLocality: RecyclerView? = null
    private var nestedview: NestedScrollView? = null
    private var recyclerViewResidentials: RecyclerView? = null
    private var arrData1: ArrayList<GetResidentialListItem> = ArrayList()
    private var arrData: ArrayList<String> = ArrayList()
    private var txtClearFilter: TextView? = null
    private var txtDone: TextView? = null
    private var ll_price: LinearLayout? = null
    private var rl_propertySale: LinearLayout? = null
    private var rl_hometype: LinearLayout? = null
    private var rl_bedroom: LinearLayout? = null
    private var filterViews: LinearLayout? = null
    private var rl_bathroom: LinearLayout? = null
    private var ll_size: LinearLayout? = null
    private var rl_house: LinearLayout? = null
    private var btn_propertyFor: TextView? = null
    private var btn_homeType: TextView? = null
    private var btn_price: TextView? = null
    private var btn_bedroom: TextView? = null
    private var btn_bathroom: TextView? = null
    private var btn_size: TextView? = null
    private var btn_openHouse: TextView? = null
    private var btnSale: TextView? = null
    private var btnRent: TextView? = null
    private var btnJudicial: TextView? = null
    private var btnHouse: TextView? = null
    private var btnCondo: TextView? = null
    private var btnTownHome: TextView? = null
    private var tvMinValue: TextView? = null
    private var tvMaxValue: TextView? = null
    private var btnAny: TextView? = null
    private var btnOne: TextView? = null
    private var btnTwo: TextView? = null
    private var btnThree: TextView? = null
    private var btnFour: TextView? = null
    private var btnAnyBathroom: TextView? = null
    private var btnOneBathroom: TextView? = null
    private var btnTwoBathroom: TextView? = null
    private var btnThreeBathroom: TextView? = null
    private var btnFourBathroom: TextView? = null
    private var tvMinValueSize: TextView? = null
    private var tvMaxValueSize: TextView? = null
    private var btnYes: TextView? = null
    private var btnNo: TextView? = null
    private var txtCountProperty: TextView? = null
    private var btn_clearAll: TextView? = null
    private var strSelectFilter: String = ""
    private var rangeSeekbar: BubbleThumbRangeSeekbar? = null
    private var rangeSeekbarSize: BubbleThumbRangeSeekbar? = null
    var loader: Loader? = null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var btnSoryBy: TextView? = null
    private var count: Int = 0
    private var strPropetyFor: String? = ""
    private var strHomeType: String? = ""
    private var strMinPrice: String? = ""
    private var strMaxPrice: String? = ""
    private var strMinSize: String? = ""
    private var strMaxSize: String? = ""
    private var strBedroom: String? = ""
    private var strBathroom: String? = ""
    private var strOpenHouse: String? = ""
    private var strSearch: String? = ""
    private var strOrder: String? = ""



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
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_residential, container, false)
        loader = Loader(activity!!)
        init(root)
        return root
    }

    private fun init(root: View) {

        ll_price = root.findViewById(R.id.ll_price) as LinearLayout
        rl_propertySale = root.findViewById(R.id.rl_propertySale) as LinearLayout
        rl_hometype = root.findViewById(R.id.rl_hometype) as LinearLayout
        rl_bedroom = root.findViewById(R.id.rl_bedroom) as LinearLayout
        filterViews = root.findViewById(R.id.filterViews) as LinearLayout
        rl_bathroom = root.findViewById(R.id.rl_bathroom) as LinearLayout
        ll_size = root.findViewById(R.id.ll_size) as LinearLayout
        rl_house = root.findViewById(R.id.rl_house) as LinearLayout

        txtCountProperty = root.findViewById(R.id.txtCountProperty) as TextView
        btn_propertyFor = root.findViewById(R.id.btn_propertyFor) as TextView
        btn_homeType = root.findViewById(R.id.btn_homeType) as TextView
        btn_price = root.findViewById(R.id.btn_price) as TextView
        btn_bedroom = root.findViewById(R.id.btn_bedroom) as TextView
        btn_bathroom = root.findViewById(R.id.btn_bathroom) as TextView
        btn_size = root.findViewById(R.id.btn_size) as TextView
        btn_openHouse = root.findViewById(R.id.btn_openHouse) as TextView
        btnSale = root.findViewById(R.id.btnSale) as TextView
        btnRent = root.findViewById(R.id.btnRent) as TextView
        btnJudicial = root.findViewById(R.id.btnJudicial) as TextView
        btnHouse = root.findViewById(R.id.btnHouse) as TextView
        btnCondo = root.findViewById(R.id.btnCondo) as TextView
        btnTownHome = root.findViewById(R.id.btnTownHome) as TextView
        tvMinValue = root.findViewById(R.id.tvMinValue) as TextView
        tvMaxValue = root.findViewById(R.id.tvMaxValue) as TextView
        btnAny = root.findViewById(R.id.btnAny) as TextView
        btnOne = root.findViewById(R.id.btnOne) as TextView
        btnTwo = root.findViewById(R.id.btnTwo) as TextView
        btnThree = root.findViewById(R.id.btnThree) as TextView
        btnFour = root.findViewById(R.id.btnFour) as TextView
        btnAnyBathroom = root.findViewById(R.id.btnAnyBathroom) as TextView
        btnOneBathroom = root.findViewById(R.id.btnOneBathroom) as TextView
        btnTwoBathroom = root.findViewById(R.id.btnTwoBathroom) as TextView
        btnThreeBathroom = root.findViewById(R.id.btnThreeBathroom) as TextView
        btnFourBathroom = root.findViewById(R.id.btnFourBathroom) as TextView
        tvMinValueSize = root.findViewById(R.id.tvMinValueSize) as TextView
        tvMaxValueSize = root.findViewById(R.id.tvMaxValueSize) as TextView
        btnYes = root.findViewById(R.id.btnYes) as TextView
        btnNo = root.findViewById(R.id.btnNo) as TextView

        rangeSeekbar = root.findViewById(R.id.rangeSeekbar) as BubbleThumbRangeSeekbar
        rangeSeekbarSize = root.findViewById(R.id.rangeSeekbarSize) as BubbleThumbRangeSeekbar

        txtClearFilter = root.findViewById(R.id.txtClearFilter) as TextView
        txtDone = root.findViewById(R.id.txtDone) as TextView
        btn_clearAll = root.findViewById(R.id.btn_clearAll) as TextView
        btnSoryBy = root.findViewById(R.id.btnSoryBy) as TextView
        recyclerViewLocality = root.findViewById(R.id.recyclerViewLocality) as RecyclerView
        nestedview = root.findViewById(R.id.nestedview) as NestedScrollView
        val mLayoutManager = LinearLayoutManager(activity)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewLocality?.layoutManager = mLayoutManager
        recyclerViewLocality?.adapter = LocalityAdapter(activity!!, arrData)

        recyclerViewResidentials = root.findViewById(R.id.recyclerViewResidentials) as RecyclerView


        btnSoryBy!!.setOnClickListener {
            showBottomSheetDialog()
        }
        getResedentialResponse(count)

        nestedview!!.getViewTreeObserver().addOnScrollChangedListener(OnScrollChangedListener {
            val view = nestedview!!.getChildAt(nestedview!!.getChildCount() - 1) as View
            val diff: Int = view.bottom - (nestedview!!.getHeight() + nestedview!!
                .getScrollY())
            if (diff == 0) {
                // your pagination code

                count = count + 12
                if (strSearch.equals("")){
                    getResedentialResponse(count)
                }else{
                    getSearchResedentialResponse(count)
                }

            }
        })

        rangeSeekbar!!.setOnRangeSeekbarChangeListener { minValue, maxValue ->
            strMinPrice = SharedPrefsConstant.Price+" gt "+ minValue.toString()+" and "
            strMaxPrice = SharedPrefsConstant.Price+" lt "+ maxValue.toString()+" and "
        }
        rangeSeekbarSize!!.setOnRangeSeekbarChangeListener { minValue, maxValue ->
//            strMinSize = minValue.toString()
//            strMaxSize = maxValue.toString()
//
//            strMinPrice = SharedPrefsConstant.Price+" lt "+ minValue.toString()+" and "
//            strMaxPrice = SharedPrefsConstant.Price+" gt "+ maxValue.toString()+" and "
        }


        //set on click listner
        btn_clearAll!!.setOnClickListener(this)
        txtClearFilter!!.setOnClickListener(this)
        txtDone!!.setOnClickListener(this)
        btn_propertyFor!!.setOnClickListener(this)
        btn_homeType!!.setOnClickListener(this)
        btn_price!!.setOnClickListener(this)
        btn_bedroom!!.setOnClickListener(this)
        btn_bathroom!!.setOnClickListener(this)
        btn_size!!.setOnClickListener(this)
        btn_openHouse!!.setOnClickListener(this)
        btnSale!!.setOnClickListener(this)
        btnRent!!.setOnClickListener(this)
        btnJudicial!!.setOnClickListener(this)
        btnHouse!!.setOnClickListener(this)
        btnCondo!!.setOnClickListener(this)
        btnTownHome!!.setOnClickListener(this)
        btnAny!!.setOnClickListener(this)
        btnOne!!.setOnClickListener(this)
        btnTwo!!.setOnClickListener(this)
        btnThree!!.setOnClickListener(this)
        btnFour!!.setOnClickListener(this)
        btnAnyBathroom!!.setOnClickListener(this)
        btnOneBathroom!!.setOnClickListener(this)
        btnTwoBathroom!!.setOnClickListener(this)
        btnThreeBathroom!!.setOnClickListener(this)
        btnFourBathroom!!.setOnClickListener(this)
        btnYes!!.setOnClickListener(this)
        btnNo!!.setOnClickListener(this)
    }


    fun showBottomSheetDialog() {
        val view: View = layoutInflater.inflate(R.layout.sort_filter, null)
        val dialog = BottomSheetDialog(activity!!)
        val heightInPixels = 750
        val params = ViewGroup.LayoutParams(MATCH_PARENT, heightInPixels)
        dialog.setContentView(view, params)
        val img_close = dialog.findViewById<ImageView>(R.id.img_close)
        val txtPopularity = dialog.findViewById<TextView>(R.id.txtPopularity)
        val txtHigh = dialog.findViewById<TextView>(R.id.txtHigh)
        val txtLow = dialog.findViewById<TextView>(R.id.txtLow)
        val txtOldest = dialog.findViewById<TextView>(R.id.txtOldest)
        val txtNewest = dialog.findViewById<TextView>(R.id.txtNewest)

        img_close!!.setOnClickListener {
            dialog.dismiss()
        }
        txtPopularity!!.setOnClickListener {
            strOrder = SharedPrefsConstant.orderby+SharedPrefsConstant.Price+" DESC and "
            getSearchResedentialResponse(12)
            dialog.dismiss()
        }
        txtHigh!!.setOnClickListener {
            strOrder = SharedPrefsConstant.orderby+SharedPrefsConstant.Price+" DESC and "
            getSearchResedentialResponse(12)
            dialog.dismiss()
        }
        txtLow!!.setOnClickListener {
            strOrder = SharedPrefsConstant.orderby+SharedPrefsConstant.Price+" ASC and "
            getSearchResedentialResponse(12)
            dialog.dismiss()
        }
        txtOldest!!.setOnClickListener {
            strOrder = SharedPrefsConstant.orderby+SharedPrefsConstant.listingContractDate+" ASC and "
            getSearchResedentialResponse(12)
            dialog.dismiss()
        }
        txtNewest!!.setOnClickListener {
            strOrder = SharedPrefsConstant.orderby+SharedPrefsConstant.listingContractDate+" DESC and "
            getSearchResedentialResponse(12)
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onPropertyClick(propertyUniqid: String) {
        val mainIntent = Intent(activity, ResidentalDetailsActivity::class.java)
        mainIntent.putExtra("ID", propertyUniqid)
        startActivity(mainIntent)
        activity?.overridePendingTransition(0, 0)

    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.btn_clearAll -> {
                btn_propertyFor!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_homeType!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_price!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_bedroom!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_bathroom!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_size!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_openHouse!!.setBackgroundResource(R.drawable.coomercial_box_border)

                btn_clearAll!!.visibility = View.GONE
                ll_price!!.visibility = View.GONE
                rl_propertySale!!.visibility = View.GONE
                rl_hometype!!.visibility = View.GONE
                rl_bedroom!!.visibility = View.GONE
                rl_bathroom!!.visibility = View.GONE
                ll_size!!.visibility = View.GONE
                rl_house!!.visibility = View.GONE
                filterViews!!.visibility = View.GONE
                strSelectFilter = ""
                clearAll()
            }
            R.id.txtClearFilter -> {
                RemoveFilter(strSelectFilter)
            }
            R.id.txtDone -> {
                btn_propertyFor!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_homeType!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_price!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_bedroom!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_bathroom!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_size!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_openHouse!!.setBackgroundResource(R.drawable.coomercial_box_border)

                btn_clearAll!!.visibility = View.GONE
                ll_price!!.visibility = View.GONE
                rl_propertySale!!.visibility = View.GONE
                rl_hometype!!.visibility = View.GONE
                rl_bedroom!!.visibility = View.GONE
                rl_bathroom!!.visibility = View.GONE
                ll_size!!.visibility = View.GONE
                rl_house!!.visibility = View.GONE
                filterViews!!.visibility = View.GONE

                getSearchResedentialResponse(12)
//                strSelectFilter = ""
            }

            R.id.btn_propertyFor -> {
                rl_propertySale!!.visibility = View.VISIBLE
                ll_price!!.visibility = View.GONE
                rl_hometype!!.visibility = View.GONE
                rl_bedroom!!.visibility = View.GONE
                rl_bathroom!!.visibility = View.GONE
                ll_size!!.visibility = View.GONE
                rl_house!!.visibility = View.GONE
                btn_propertyFor!!.setBackgroundResource(R.drawable.rounded_yellow_border)
                btn_homeType!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_price!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_bedroom!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_bathroom!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_size!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_openHouse!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_clearAll!!.visibility = View.VISIBLE
                filterViews!!.visibility = View.VISIBLE
                strSelectFilter = "PropertyType"
            }
            R.id.btn_homeType -> {
                rl_propertySale!!.visibility = View.GONE
                ll_price!!.visibility = View.GONE
                rl_hometype!!.visibility = View.VISIBLE
                rl_bedroom!!.visibility = View.GONE
                rl_bathroom!!.visibility = View.GONE
                ll_size!!.visibility = View.GONE
                rl_house!!.visibility = View.GONE
                btn_homeType!!.setBackgroundResource(R.drawable.rounded_yellow_border)
                btn_propertyFor!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_price!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_bedroom!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_bathroom!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_size!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_openHouse!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_clearAll!!.visibility = View.VISIBLE
                filterViews!!.visibility = View.VISIBLE
                strSelectFilter = "HomeType"
            }
            R.id.btn_price -> {
                rl_propertySale!!.visibility = View.GONE
                ll_price!!.visibility = View.VISIBLE
                rl_hometype!!.visibility = View.GONE
                rl_bedroom!!.visibility = View.GONE
                rl_bathroom!!.visibility = View.GONE
                ll_size!!.visibility = View.GONE
                rl_house!!.visibility = View.GONE
                btn_price!!.setBackgroundResource(R.drawable.rounded_yellow_border)
                btn_propertyFor!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_homeType!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_bedroom!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_bathroom!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_size!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_openHouse!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_clearAll!!.visibility = View.VISIBLE
                filterViews!!.visibility = View.VISIBLE
                strSelectFilter = "Price"
            }
            R.id.btn_bedroom -> {
                rl_propertySale!!.visibility = View.GONE
                ll_price!!.visibility = View.GONE
                rl_hometype!!.visibility = View.GONE
                rl_bedroom!!.visibility = View.VISIBLE
                rl_bathroom!!.visibility = View.GONE
                ll_size!!.visibility = View.GONE
                rl_house!!.visibility = View.GONE
                btn_bedroom!!.setBackgroundResource(R.drawable.rounded_yellow_border)
                btn_propertyFor!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_homeType!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_price!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_bathroom!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_size!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_openHouse!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_clearAll!!.visibility = View.VISIBLE
                filterViews!!.visibility = View.VISIBLE
                strSelectFilter = "Bedroom"
            }
            R.id.btn_bathroom -> {
                rl_propertySale!!.visibility = View.GONE
                ll_price!!.visibility = View.GONE
                rl_hometype!!.visibility = View.GONE
                rl_bedroom!!.visibility = View.GONE
                rl_bathroom!!.visibility = View.VISIBLE
                ll_size!!.visibility = View.GONE
                rl_house!!.visibility = View.GONE
                btn_bathroom!!.setBackgroundResource(R.drawable.rounded_yellow_border)
                btn_propertyFor!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_homeType!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_price!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_bedroom!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_size!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_openHouse!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_clearAll!!.visibility = View.VISIBLE
                filterViews!!.visibility = View.VISIBLE
                strSelectFilter = "Bathroom"
            }
            R.id.btn_size -> {
                rl_propertySale!!.visibility = View.GONE
                ll_price!!.visibility = View.GONE
                rl_hometype!!.visibility = View.GONE
                rl_bedroom!!.visibility = View.GONE
                rl_bathroom!!.visibility = View.GONE
                ll_size!!.visibility = View.VISIBLE
                rl_house!!.visibility = View.GONE
                btn_size!!.setBackgroundResource(R.drawable.rounded_yellow_border)
                btn_propertyFor!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_homeType!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_price!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_bedroom!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_bathroom!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_openHouse!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_clearAll!!.visibility = View.VISIBLE
                filterViews!!.visibility = View.VISIBLE
                strSelectFilter = "Size"
            }
            R.id.btn_openHouse -> {
                rl_propertySale!!.visibility = View.GONE
                ll_price!!.visibility = View.GONE
                rl_hometype!!.visibility = View.GONE
                rl_bedroom!!.visibility = View.GONE
                rl_bathroom!!.visibility = View.GONE
                ll_size!!.visibility = View.GONE
                rl_house!!.visibility = View.VISIBLE
                btn_openHouse!!.setBackgroundResource(R.drawable.rounded_yellow_border)
                btn_propertyFor!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_homeType!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_price!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_bedroom!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_bathroom!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_size!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_clearAll!!.visibility = View.VISIBLE
                filterViews!!.visibility = View.VISIBLE
                strSelectFilter = "Open House"
            }
            R.id.btnSale -> {
                strPropetyFor = SharedPrefsConstant.TransactionType+" eq %27For Sale%27 and "
                btnSale!!.setBackgroundResource(R.drawable.yellow_rounded_border)
                btnRent!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnJudicial!!.setBackgroundResource(R.drawable.rounded_grey_border)
            }
            R.id.btnRent -> {
                strPropetyFor = SharedPrefsConstant.TransactionType+" eq %27Rent%27 and "
                btnRent!!.setBackgroundResource(R.drawable.yellow_rounded_border)
                btnSale!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnJudicial!!.setBackgroundResource(R.drawable.rounded_grey_border)
            }
            R.id.btnJudicial -> {
                strPropetyFor = SharedPrefsConstant.TransactionType+" eq %27Judicial Sale%27 and "
                btnJudicial!!.setBackgroundResource(R.drawable.yellow_rounded_border)
                btnRent!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnSale!!.setBackgroundResource(R.drawable.rounded_grey_border)
            }
            R.id.btnHouse -> {
                strHomeType =SharedPrefsConstant.TransactionType+ " eq %27House%27 and "
                btnHouse!!.setBackgroundResource(R.drawable.yellow_rounded_border)
                btnCondo!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnTownHome!!.setBackgroundResource(R.drawable.rounded_grey_border)
            }
            R.id.btnCondo -> {
                strHomeType = "eq %27Condo%27"
                btnCondo!!.setBackgroundResource(R.drawable.yellow_rounded_border)
                btnHouse!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnTownHome!!.setBackgroundResource(R.drawable.rounded_grey_border)
            }
            R.id.btnTownHome -> {
                strHomeType = "eq %27Townhomes%27"
                btnTownHome!!.setBackgroundResource(R.drawable.yellow_rounded_border)
                btnHouse!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnCondo!!.setBackgroundResource(R.drawable.rounded_grey_border)
            }
            R.id.btnAny -> {
                strBedroom = SharedPrefsConstant.bedroomsTotal+" eq %27Any%27 and "
                btnAny!!.setBackgroundResource(R.drawable.yellow_rounded_border)
                btnOne!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnTwo!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnThree!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnFour!!.setBackgroundResource(R.drawable.rounded_grey_border)
            }
            R.id.btnOne -> {
                strBedroom = SharedPrefsConstant.bedroomsTotal+" eq 1 and "
                btnAny!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnOne!!.setBackgroundResource(R.drawable.yellow_rounded_border)
                btnTwo!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnThree!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnFour!!.setBackgroundResource(R.drawable.rounded_grey_border)
            }
            R.id.btnTwo -> {
                strBedroom = SharedPrefsConstant.bedroomsTotal+" eq 2 and "
                btnAny!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnOne!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnTwo!!.setBackgroundResource(R.drawable.yellow_rounded_border)
                btnThree!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnFour!!.setBackgroundResource(R.drawable.rounded_grey_border)
            }
            R.id.btnThree -> {
                strBedroom = SharedPrefsConstant.bedroomsTotal+" eq 3 and "
                btnAny!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnOne!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnTwo!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnThree!!.setBackgroundResource(R.drawable.yellow_rounded_border)
                btnFour!!.setBackgroundResource(R.drawable.rounded_grey_border)
            }
            R.id.btnFour -> {
                strBedroom = SharedPrefsConstant.bedroomsTotal+" eq 4 and "
                btnAny!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnOne!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnTwo!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnThree!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnFour!!.setBackgroundResource(R.drawable.yellow_rounded_border)
            }
            R.id.btnAnyBathroom -> {
                strBathroom = SharedPrefsConstant.bathroomTotal+" eq %27Any%27 and "
                btnAnyBathroom!!.setBackgroundResource(R.drawable.yellow_rounded_border)
                btnOneBathroom!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnTwoBathroom!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnThreeBathroom!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnFourBathroom!!.setBackgroundResource(R.drawable.rounded_grey_border)
            }
            R.id.btnOneBathroom -> {
                strBathroom = SharedPrefsConstant.bathroomTotal+" eq 1 and "
                btnAnyBathroom!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnOneBathroom!!.setBackgroundResource(R.drawable.yellow_rounded_border)
                btnTwoBathroom!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnThreeBathroom!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnFourBathroom!!.setBackgroundResource(R.drawable.rounded_grey_border)
            }
            R.id.btnTwoBathroom -> {
                strBathroom = SharedPrefsConstant.bathroomTotal+" eq 2 and "
                btnAnyBathroom!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnOneBathroom!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnTwoBathroom!!.setBackgroundResource(R.drawable.yellow_rounded_border)
                btnThreeBathroom!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnFourBathroom!!.setBackgroundResource(R.drawable.rounded_grey_border)
            }
            R.id.btnThreeBathroom -> {
                strBathroom = SharedPrefsConstant.bathroomTotal+" eq 3 and "
                btnAnyBathroom!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnOneBathroom!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnTwoBathroom!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnThreeBathroom!!.setBackgroundResource(R.drawable.yellow_rounded_border)
                btnFourBathroom!!.setBackgroundResource(R.drawable.rounded_grey_border)
            }
            R.id.btnFourBathroom -> {
                strBathroom = SharedPrefsConstant.bathroomTotal+" eq 4 and "
                btnAnyBathroom!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnOneBathroom!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnTwoBathroom!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnThreeBathroom!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnFourBathroom!!.setBackgroundResource(R.drawable.yellow_rounded_border)
            }
            R.id.btnYes -> {
                strOpenHouse = SharedPrefsConstant.bathroomTotal+" eq %27Yes%27 and "
                btnYes!!.setBackgroundResource(R.drawable.yellow_rounded_border)
                btnNo!!.setBackgroundResource(R.drawable.rounded_grey_border)
            }
            R.id.btnNo -> {
                strOpenHouse = SharedPrefsConstant.bathroomTotal+" eq %No%27 and "
                btnYes!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnNo!!.setBackgroundResource(R.drawable.yellow_rounded_border)
            }
        }
    }

    private fun clearAll() {
        btn_propertyFor!!.setBackgroundResource(R.drawable.coomercial_box_border)
        btn_homeType!!.setBackgroundResource(R.drawable.coomercial_box_border)
        btn_price!!.setBackgroundResource(R.drawable.coomercial_box_border)
        btn_bedroom!!.setBackgroundResource(R.drawable.coomercial_box_border)
        btn_bathroom!!.setBackgroundResource(R.drawable.coomercial_box_border)
        btn_size!!.setBackgroundResource(R.drawable.coomercial_box_border)
        btn_openHouse!!.setBackgroundResource(R.drawable.coomercial_box_border)
        btnSale!!.setBackgroundResource(R.drawable.rounded_grey_border)
        btnRent!!.setBackgroundResource(R.drawable.rounded_grey_border)
        btnJudicial!!.setBackgroundResource(R.drawable.rounded_grey_border)
        btnHouse!!.setBackgroundResource(R.drawable.rounded_grey_border)
        btnCondo!!.setBackgroundResource(R.drawable.rounded_grey_border)
        btnTownHome!!.setBackgroundResource(R.drawable.rounded_grey_border)
        btnAny!!.setBackgroundResource(R.drawable.rounded_grey_border)
        btnOne!!.setBackgroundResource(R.drawable.rounded_grey_border)
        btnTwo!!.setBackgroundResource(R.drawable.rounded_grey_border)
        btnThree!!.setBackgroundResource(R.drawable.rounded_grey_border)
        btnFour!!.setBackgroundResource(R.drawable.rounded_grey_border)
        btnAnyBathroom!!.setBackgroundResource(R.drawable.rounded_grey_border)
        btnOneBathroom!!.setBackgroundResource(R.drawable.rounded_grey_border)
        btnTwoBathroom!!.setBackgroundResource(R.drawable.rounded_grey_border)
        btnThreeBathroom!!.setBackgroundResource(R.drawable.rounded_grey_border)
        btnFourBathroom!!.setBackgroundResource(R.drawable.rounded_grey_border)
        btnYes!!.setBackgroundResource(R.drawable.rounded_grey_border)
        btnNo!!.setBackgroundResource(R.drawable.rounded_grey_border)
        rangeSeekbar!!.setMinValue(100000F)
        rangeSeekbar!!.setMaxValue(1000000F)
        rangeSeekbarSize!!.setMinValue(0F)
        rangeSeekbarSize!!.setMaxValue(100000F)
        tvMaxValue!!.text = "1000000"
        tvMaxValueSize!!.text = "100000"
        tvMinValue!!.text = "100000"
        tvMinValueSize!!.text = "0"

    }

    private fun RemoveFilter(strSelectFilter: String) {
        if (strSelectFilter == "PropertyType") {
            btn_propertyFor!!.setBackgroundResource(R.drawable.coomercial_box_border)
        } else if (strSelectFilter == "Price") {
            btn_price!!.setBackgroundResource(R.drawable.coomercial_box_border)
            rangeSeekbar!!.setMinValue(100000F)
            rangeSeekbar!!.setMaxValue(1000000F)
            tvMaxValue!!.text = "1000000"
            tvMinValue!!.text = "100000"
        } else if (strSelectFilter == "HomeType") {
            btn_homeType!!.setBackgroundResource(R.drawable.coomercial_box_border)

        } else if (strSelectFilter == "Bedroom") {
            btn_bedroom!!.setBackgroundResource(R.drawable.coomercial_box_border)

        } else if (strSelectFilter == "Bathroom") {
            btn_bathroom!!.setBackgroundResource(R.drawable.coomercial_box_border)

        } else if (strSelectFilter == "Size") {
            btn_size!!.setBackgroundResource(R.drawable.coomercial_box_border)

        } else if (strSelectFilter == "Open House") {
            btn_openHouse!!.setBackgroundResource(R.drawable.coomercial_box_border)

        }
//        else if (strSelectFilter == "Sale") {
//            btnSale!!.setBackgroundResource(R.drawable.rounded_grey_border)
//        } else if (strSelectFilter == "Lease") {
//            btnLease!!.setBackgroundResource(R.drawable.rounded_grey_border)
//        } else if (strSelectFilter == "Sale") {
//            btnSale_Lease!!.setBackgroundResource(R.drawable.rounded_grey_border)
//        }
    }


    private fun getSearchResedentialResponse(count: Int) {
        strSearch = "Yes"
        if (count > 12) {
            call = RetrofitClient.getClient()
                    .getResedentialProperty("https://patelestateapi-dev.azurewebsites.net/api/ResidentialProperty?%24top=12&%24skip=" + count+"&%24filter="+strPropetyFor+strBedroom+strBathroom+strMinPrice+strMaxPrice+strOrder)
        }
        else {
            loader!!.show()

            if (strOrder.equals("")){
                val url = "https://patelestateapi-dev.azurewebsites.net/api/ResidentialProperty?%24top=12&"+SharedPrefsConstant.Filterby+strPropetyFor+strBedroom+strBathroom+strMinPrice+strMaxPrice
                val a = url!!.substring(0, url!!.length - 4)
                Log.d("++++++++++++Url",a)

                call = RetrofitClient.getClient()
                        .getResedentialProperty(a)
            }else{
                val url = "https://patelestateapi-dev.azurewebsites.net/api/ResidentialProperty?%24top=12&"+strOrder
                val a = url!!.substring(0, url!!.length - 4)
                Log.d("++++++++++++Url",a)

                call = RetrofitClient.getClient()
                        .getResedentialProperty(a)
            }
        }

        call!!.enqueue(object : Callback<JsonArray> {

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.e("error data", call.toString())
                loader!!.cancel()
            }

            override fun onResponse(
                call: Call<JsonArray>,
                response: retrofit2.Response<JsonArray>
            ) {
                loader!!.cancel()
                arrData1 = ArrayList()
                if (response.code().equals(200)){
                    val RegisterResponse = response!!.body()!!
                    Log.e("Data", "Done")

                    val jsonArray = JSONArray(RegisterResponse.toString())
                    Log.e("Data", RegisterResponse.toString())
                    Log.e("JsornArray", jsonArray.toString())
                    txtCountProperty!!.setText(jsonArray.length().toString()+ " Properties Found")
                    for (i in 0 until jsonArray.length()) {
                        val jsObj: JSONObject = jsonArray.getJSONObject(i)

                        val abjd = GetResidentialListItem(
                                jsObj.getString("propertyUniqid"), jsObj.getString("propertyType"),
                                jsObj.getString("mlsId"), jsObj.getString("listingContractDate"),
                                jsObj.getString("bathroomTotal"), jsObj.getString("bedroomsTotal"),
                                jsObj.getString("totalFinishedArea"), jsObj.getString("address"),
                                jsObj.getString("streetAddress"), jsObj.getString("city"),
                                jsObj.getString("postalCode"), jsObj.getString("province"),
                                jsObj.getString("communityName"), jsObj.getString("zoningDescription"),
                                jsObj.getString("openHouse"), jsObj.getString("price"),
                                jsObj.getString("latitude"), jsObj.getString("longitude"),
                                jsObj.getString("transactionType"), jsObj.getString("photoPath"),
                                jsObj.getString("exclusive"), jsObj.getString("forCloser")
                        )

                        if (!arrData1.contains(abjd))
                            arrData1.add(abjd)
                    }


                    Log.e("arrData1", arrData1.toString())
                    recyclerViewResidentials?.adapter = ResidencialPropertiesAdapter(
                            activity!!,
                            arrData1,
                            this@ResidentialFragment
                    )
                }else{

                }


            }
        })


    }

    private fun getResedentialResponse(count: Int) {
        if (count > 12) {
            call = RetrofitClient.getClient()
                    .getResedentialProperty("https://patelestateapi-dev.azurewebsites.net/api/ResidentialProperty?%24top=12&%24skip=" + count)
        } else {
            loader!!.show()
             call = RetrofitClient.getClient()
                    .getResedentialProperty("https://patelestateapi-dev.azurewebsites.net/api/ResidentialProperty?%24top="+12)
        }


        call!!.enqueue(object : Callback<JsonArray> {

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.e("error data", call.toString())
                Log.e("Hello", "hello")
                loader!!.cancel()
            }

            override fun onResponse(
                    call: Call<JsonArray>,
                    response: retrofit2.Response<JsonArray>
            ) {
                loader!!.cancel()
                if (response.code().equals(200)){
                    val RegisterResponse = response!!.body()!!
                    Log.e("Data", "Done")

                    val jsonArray = JSONArray(RegisterResponse.toString())
                    Log.e("Data", RegisterResponse.toString())
                    Log.e("JsornArray", jsonArray.toString())
                    txtCountProperty!!.setText(jsonArray.length().toString()+ " Properties Found")
                    for (i in 0 until jsonArray.length()) {
                        val jsObj: JSONObject = jsonArray.getJSONObject(i)

                        val abjd = GetResidentialListItem(
                                jsObj.getString("propertyUniqid"), jsObj.getString("propertyType"),
                                jsObj.getString("mlsId"), jsObj.getString("listingContractDate"),
                                jsObj.getString("bathroomTotal"), jsObj.getString("bedroomsTotal"),
                                jsObj.getString("totalFinishedArea"), jsObj.getString("address"),
                                jsObj.getString("streetAddress"), jsObj.getString("city"),
                                jsObj.getString("postalCode"), jsObj.getString("province"),
                                jsObj.getString("communityName"), jsObj.getString("zoningDescription"),
                                jsObj.getString("openHouse"), jsObj.getString("price"),
                                jsObj.getString("latitude"), jsObj.getString("longitude"),
                                jsObj.getString("transactionType"), jsObj.getString("photoPath"),
                                jsObj.getString("exclusive"), jsObj.getString("forCloser")
                        )

                        if (!arrData1.contains(abjd))
                            arrData1.add(abjd)
                    }
                    Log.e("arrData1", arrData1.toString())
                    recyclerViewResidentials?.adapter = ResidencialPropertiesAdapter(
                            activity!!,
                            arrData1,
                            this@ResidentialFragment
                    )
                }else
                {

                }


//
//

            }
        })


    }


}