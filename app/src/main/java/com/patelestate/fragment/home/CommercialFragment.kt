package com.patelestate.fragment.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crownfantancy.ui.Retrofit.RetrofitClient
import com.crystal.crystalrangeseekbar.widgets.BubbleThumbRangeSeekbar
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonArray
import com.patelestate.Loader
import com.patelestate.R
import com.patelestate.adapter.CommercialPropertiesAdapter
import com.patelestate.adapter.PropertyTypeAdapter
import com.patelestate.fragment.home.commercial.CommercialDetailsActivity
import com.patelestate.model.GetCommercialLIstItem
import com.patelestate.utils.SharedPrefsConstant
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CommercialFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CommercialFragment : Fragment(), CommercialPropertiesAdapter.onCommercialPropertyClick,PropertyTypeAdapter.onPropertyTypeClick,
        View.OnClickListener {
    private var call: Call<JsonArray>? = null
    private var url: String? = null
    private var dialog: android.app.AlertDialog? = null
    private var strOrder: String? = ""
    private var arrProperty: ArrayList<String>? = null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var recyclerViewCommercial: RecyclerView? = null
    private var recyclerViewProperty: RecyclerView? = null
    private var arrData1: ArrayList<GetCommercialLIstItem> = ArrayList()
    private var btn_clearAll: TextView? = null
    private var btn_propertyType: TextView? = null
    private var tvNumberOfProperties: TextView? = null
    private var btn_price: TextView? = null
    private var btnSoryBy: TextView? = null
    private var tvMinValue: TextView? = null
    private var tvMaxValue: TextView? = null
    private var rangeSeekbar: BubbleThumbRangeSeekbar? = null
    private var btn_propertySale: TextView? = null
    private var btnSale: TextView? = null
    private var btnLease: TextView? = null
    private var btnSale_Lease: TextView? = null
    private var txtClearFilter: TextView? = null
    private var txtDone: TextView? = null
    private var rl_property: LinearLayout? = null
    private var ll_price: LinearLayout? = null
    private var rl_propertySale: LinearLayout? = null
    private var filterViews: LinearLayout? = null
    private var strSelectFilter: String = ""
    private var count: Int = 0
    private var nestedview: NestedScrollView? = null
    var loadmore = true
    var loader: Loader? = null
    var _areLecturesLoaded = false


    private var strPropetyFor: String? = ""
    private var strMinPrice: String? = ""
    private var strMaxPrice: String? = ""
    private var strSearch: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && !_areLecturesLoaded) {
            CommercialListResponse(count)
            _areLecturesLoaded = true
        }

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_commercial, container, false)
        arrProperty = ArrayList()
        arrProperty!!.add("All")
        arrProperty!!.add("Land")
        arrProperty!!.add("Office")
        arrProperty!!.add("Industrial")
        arrProperty!!.add("Autobody")
        arrProperty!!.add("Car Wash")
        arrProperty!!.add("Personal Services")
        init(root)
        return root
    }


    private fun init(root: View) {
        loader = Loader(activity!!)
        txtClearFilter = root.findViewById(R.id.txtClearFilter) as TextView
        txtDone = root.findViewById(R.id.txtDone) as TextView
        btn_clearAll = root.findViewById(R.id.btn_clearAll) as TextView
        btn_propertyType = root.findViewById(R.id.btn_propertyType) as TextView
        btn_price = root.findViewById(R.id.btn_price) as TextView
        tvMinValue = root.findViewById(R.id.tvMinValue) as TextView
        btnSoryBy = root.findViewById(R.id.btnSoryBy) as TextView
        tvNumberOfProperties = root.findViewById(R.id.tvNumberOfProperties) as TextView
        tvMaxValue = root.findViewById(R.id.tvMaxValue) as TextView
        rangeSeekbar = root.findViewById(R.id.rangeSeekbar) as BubbleThumbRangeSeekbar
        btn_propertySale = root.findViewById(R.id.btn_propertySale) as TextView
        btnSale = root.findViewById(R.id.btnSale) as TextView
        btnLease = root.findViewById(R.id.btnLease) as TextView
        btnSale_Lease = root.findViewById(R.id.btnSale_Lease) as TextView
        rl_property = root.findViewById(R.id.rl_property) as LinearLayout
        rl_propertySale = root.findViewById(R.id.rl_propertySale) as LinearLayout
        ll_price = root.findViewById(R.id.ll_price) as LinearLayout
        filterViews = root.findViewById(R.id.filterViews) as LinearLayout
        recyclerViewCommercial = root.findViewById(R.id.recyclerViewCommercial) as RecyclerView
        recyclerViewProperty = root.findViewById(R.id.recyclerViewProperty) as RecyclerView
        nestedview = root.findViewById(R.id.nestedview1) as NestedScrollView
        recyclerViewCommercial?.layoutManager = LinearLayoutManager(activity)
        nestedview!!.viewTreeObserver.addOnScrollChangedListener(ViewTreeObserver.OnScrollChangedListener {
            val view = nestedview!!.getChildAt(nestedview!!.childCount - 1) as View
            val diff: Int = view.bottom - (nestedview!!.height + nestedview!!
                    .scrollY)
            if (diff == 0) {
                // your pagination code

                count = count + 12
                if (strSearch.equals("")) {
                    CommercialListResponse(count)
                } else {
                    getSearchCommercialResponse(count)
                }

            }
        })
        val adapterProperty = PropertyTypeAdapter(activity!!,arrProperty!!,this)
        recyclerViewProperty!!.adapter = adapterProperty


        //set on click listner
        btn_clearAll!!.setOnClickListener(this)
        btn_propertyType!!.setOnClickListener(this)
        btn_price!!.setOnClickListener(this)
        btn_propertySale!!.setOnClickListener(this)
        txtClearFilter!!.setOnClickListener(this)
        btnSale!!.setOnClickListener(this)
        btnLease!!.setOnClickListener(this)
        btnSale_Lease!!.setOnClickListener(this)
        txtDone!!.setOnClickListener(this)
        btnSoryBy!!.setOnClickListener {
            showBottomSheetDialog()
        }

        rangeSeekbar!!.setOnRangeSeekbarChangeListener { minValue, maxValue ->
            strMinPrice = SharedPrefsConstant.askingPrice + " gt " + minValue.toString() + " and "
            strMaxPrice = SharedPrefsConstant.askingPrice + " lt " + maxValue.toString() + " and "
        }


    }

    fun showBottomSheetDialog() {
        val view: View = layoutInflater.inflate(R.layout.sort_filter, null)
        val dialog = BottomSheetDialog(activity!!)
        val heightInPixels = 750
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightInPixels)
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
            strOrder = SharedPrefsConstant.orderby + SharedPrefsConstant.askingPrice + " DESC and "
            getSearchCommercialResponse(12)
            dialog.dismiss()
        }
        txtHigh!!.setOnClickListener {
            strOrder = SharedPrefsConstant.orderby + SharedPrefsConstant.askingPrice + " DESC and "
            getSearchCommercialResponse(12)
            dialog.dismiss()
        }
        txtLow!!.setOnClickListener {
            strOrder = SharedPrefsConstant.orderby + SharedPrefsConstant.askingPrice + " ASC and "
            getSearchCommercialResponse(12)
            dialog.dismiss()
        }
        txtOldest!!.setOnClickListener {
            strOrder = SharedPrefsConstant.orderby + SharedPrefsConstant.listingDate + " ASC and "
            getSearchCommercialResponse(12)
            dialog.dismiss()
        }
        txtNewest!!.setOnClickListener {
            strOrder = SharedPrefsConstant.orderby + SharedPrefsConstant.listingDate + " DESC and "
            getSearchCommercialResponse(12)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun CommercialListResponse(count: Int) {
        if (count > 12) {
            call = RetrofitClient.getClient()
                    .GetCommercialList("https://patelestateapi-dev.azurewebsites.net/api/CommercialProperties?%24top=12&%24skip=" + count)

        } else {
            loader!!.show()

            call = RetrofitClient.getClient()
                    .GetCommercialList("https://patelestateapi-dev.azurewebsites.net/api/CommercialProperties?%24top=12")

        }

        call!!.enqueue(object : Callback<JsonArray> {

            override fun onFailure(call: Call<JsonArray>?, t: Throwable) {
                Log.e("error data", call.toString())
                loader!!.cancel()
            }

            override fun onResponse(
                    call: Call<JsonArray>?,
                    response: retrofit2.Response<JsonArray>?
            ) {
                loader!!.cancel()
                if (response!!.code().equals(200)){
                    val RegisterResponse = response!!.body()!!
                    Log.e("Data", "Done")

                    val jsonArray = JSONArray(RegisterResponse.toString())
                    Log.e("Data", RegisterResponse.toString())
                    Log.e("JsornArray", jsonArray.toString())
                    tvNumberOfProperties!!.text = jsonArray.length().toString() + " Properties Found"
                    for (i in 0 until jsonArray.length()) {
                        val jsObj: JSONObject = jsonArray.getJSONObject(i)

                        val abjd = GetCommercialLIstItem(
                                jsObj.getString("address"), jsObj.getString("askingPrice"),
                                jsObj.getString("baseRent"), jsObj.getString("city"),
                                jsObj.getString("comRent"), jsObj.getString("exclusive"),
                                jsObj.getString("hospitalityType"), jsObj.getString("landArea"),
                                jsObj.getString("latitude"), jsObj.getString("listingDate"),
                                jsObj.getString("longitude"), jsObj.getString("mlsId"),
                                jsObj.getString("photoPath"), jsObj.getString("postalCode"),
                                jsObj.getString("propertyType"), jsObj.getString("propertyUniqid"),
                                jsObj.getString("state"), jsObj.getString("transactionType")
                        )
                        if (!arrData1.contains(abjd))
                            arrData1.add(abjd)
                    }

                    Log.e("arrData1", arrData1.toString())
                    Log.e("size", arrData1.size.toString())
                    recyclerViewCommercial?.adapter = CommercialPropertiesAdapter(
                            activity!!,
                            arrData1,
                            this@CommercialFragment
                    )
                }else{

                }

            }
        })
    }


    override fun onPropertyClick(propertyUniqid: String) {
        val mainIntent = Intent(activity, CommercialDetailsActivity::class.java)
        mainIntent.putExtra("ID", propertyUniqid)
        startActivity(mainIntent)
        activity?.overridePendingTransition(0, 0)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_clearAll -> {
                btn_propertyType!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_price!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_propertySale!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btnSale!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnLease!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnSale_Lease!!.setBackgroundResource(R.drawable.rounded_grey_border)

                btn_clearAll!!.visibility = View.GONE
                rl_property!!.visibility = View.GONE
                ll_price!!.visibility = View.GONE
                rl_propertySale!!.visibility = View.GONE
                filterViews!!.visibility = View.GONE
                strSelectFilter = ""
                clearAll()
            }

            R.id.btn_propertyType -> {
                rl_property!!.visibility = View.VISIBLE
                ll_price!!.visibility = View.GONE
                rl_propertySale!!.visibility = View.GONE
                btn_propertyType!!.setBackgroundResource(R.drawable.rounded_yellow_border)
                btn_clearAll!!.visibility = View.VISIBLE
                filterViews!!.visibility = View.VISIBLE
                strSelectFilter = "PropertyType"
            }
            R.id.btn_price -> {
                rl_property!!.visibility = View.GONE
                rl_propertySale!!.visibility = View.GONE
                ll_price!!.visibility = View.VISIBLE
                btn_price!!.setBackgroundResource(R.drawable.rounded_yellow_border)
                btn_clearAll!!.visibility = View.VISIBLE
                filterViews!!.visibility = View.VISIBLE
                strSelectFilter = "Price"
            }
            R.id.btn_propertySale -> {
                rl_property!!.visibility = View.GONE
                ll_price!!.visibility = View.GONE
                rl_propertySale!!.visibility = View.VISIBLE
                btn_propertySale!!.setBackgroundResource(R.drawable.rounded_yellow_border)
                btn_clearAll!!.visibility = View.VISIBLE
                filterViews!!.visibility = View.VISIBLE
                strSelectFilter = "PropertySale"
            }
            R.id.txtClearFilter -> {
                RemoveFilter(strSelectFilter)
            }
            R.id.txtDone -> {
                btn_propertyType!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_price!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_propertySale!!.setBackgroundResource(R.drawable.coomercial_box_border)
                btn_clearAll!!.visibility = View.GONE
                rl_property!!.visibility = View.GONE
                ll_price!!.visibility = View.GONE
                rl_propertySale!!.visibility = View.GONE
                filterViews!!.visibility = View.GONE
                getSearchCommercialResponse(12)
            }
            R.id.btnSale -> {
                strPropetyFor = SharedPrefsConstant.TransactionType + " eq %27For Sale%27 and "
                btnSale!!.setBackgroundResource(R.drawable.yellow_rounded_border)
                btnLease!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnSale_Lease!!.setBackgroundResource(R.drawable.rounded_grey_border)
            }
            R.id.btnLease -> {
                strPropetyFor = SharedPrefsConstant.TransactionType + " eq %27Lease%27 and "
                btnSale!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnLease!!.setBackgroundResource(R.drawable.yellow_rounded_border)
                btnSale_Lease!!.setBackgroundResource(R.drawable.rounded_grey_border)
            }
            R.id.btnSale_Lease -> {
                strPropetyFor =
                        SharedPrefsConstant.TransactionType + " eq %27For Sale %26 Lease%27 and "
                btnSale!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnLease!!.setBackgroundResource(R.drawable.rounded_grey_border)
                btnSale_Lease!!.setBackgroundResource(R.drawable.yellow_rounded_border)
            }
        }
    }

    private fun RemoveFilter(strSelectFilter: String) {
        if (strSelectFilter == "PropertyType") {
            btn_propertyType!!.setBackgroundResource(R.drawable.coomercial_box_border)
        } else if (strSelectFilter == "Price") {
            btn_price!!.setBackgroundResource(R.drawable.coomercial_box_border)
            rangeSeekbar!!.setMinValue(0F)
            rangeSeekbar!!.setMaxValue(100000F)
            tvMaxValue!!.text = "1000000"
            tvMinValue!!.text = "100000"
        } else if (strSelectFilter == "PropertySale") {
            btn_propertySale!!.setBackgroundResource(R.drawable.coomercial_box_border)
            btnSale!!.setBackgroundResource(R.drawable.rounded_grey_border)
            btnLease!!.setBackgroundResource(R.drawable.rounded_grey_border)
            btnSale_Lease!!.setBackgroundResource(R.drawable.rounded_grey_border)
        }

    }

    private fun clearAll() {
        btn_propertyType!!.setBackgroundResource(R.drawable.coomercial_box_border)
        btn_price!!.setBackgroundResource(R.drawable.coomercial_box_border)
        rangeSeekbar!!.setMinValue(0F)
        rangeSeekbar!!.setMaxValue(100000F)
        tvMaxValue!!.text = "1000000"
        tvMinValue!!.text = "100000"
        btn_propertySale!!.setBackgroundResource(R.drawable.coomercial_box_border)
        btnSale!!.setBackgroundResource(R.drawable.rounded_grey_border)
        btnLease!!.setBackgroundResource(R.drawable.rounded_grey_border)
        btnSale_Lease!!.setBackgroundResource(R.drawable.rounded_grey_border)
    }


    private fun getSearchCommercialResponse(count: Int) {
        strSearch = "Yes"
        if (count > 12) {
            call = RetrofitClient.getClient()
                    .GetCommercialList("https://patelestateapi-dev.azurewebsites.net/api/CommercialProperties?%24top=12&%24skip=" + count + "&%24filter=" + strPropetyFor + strMinPrice + strMaxPrice)
        } else {
            loader!!.show()
            if (strOrder.equals("")) {
                val url = "https://patelestateapi-dev.azurewebsites.net/api/CommercialProperties?%24top=12&" + SharedPrefsConstant.Filterby + strPropetyFor + strMinPrice + strMaxPrice
                val a = url.substring(0, url.length - 4)
                Log.d("++++++++++++Url", a)

                call = RetrofitClient.getClient()
                        .GetCommercialList(a)
            } else {
                val url = "https://patelestateapi-dev.azurewebsites.net/api/CommercialProperties?%24top=12&" + strOrder
                val a = url.substring(0, url.length - 4)
                Log.d("++++++++++++Url", a)

                call = RetrofitClient.getClient()
                        .GetCommercialList(a)
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
                    val RegisterResponse = response.body()!!
                    Log.e("Data", "Done")

                    val jsonArray = JSONArray(RegisterResponse.toString())
                    Log.e("Data", RegisterResponse.toString())
                    Log.e("JsornArray", jsonArray.toString())
                    strPropetyFor = ""
                    strMaxPrice = ""
                    strMinPrice = ""
                    tvNumberOfProperties!!.text = jsonArray.length().toString() + " Properties Found"
                    for (i in 0 until jsonArray.length()) {
                        val jsObj: JSONObject = jsonArray.getJSONObject(i)

                        val abjd = GetCommercialLIstItem(
                                jsObj.getString("address"), jsObj.getString("askingPrice"),
                                jsObj.getString("baseRent"), jsObj.getString("city"),
                                jsObj.getString("comRent"), jsObj.getString("exclusive"),
                                jsObj.getString("hospitalityType"), jsObj.getString("landArea"),
                                jsObj.getString("latitude"), jsObj.getString("listingDate"),
                                jsObj.getString("longitude"), jsObj.getString("mlsId"),
                                jsObj.getString("photoPath"), jsObj.getString("postalCode"),
                                jsObj.getString("propertyType"), jsObj.getString("propertyUniqid"),
                                jsObj.getString("state"), jsObj.getString("transactionType")
                        )
                        if (!arrData1.contains(abjd))
                            arrData1.add(abjd)
                    }

                    Log.e("arrData1", arrData1.toString())
                    Log.e("size", arrData1.size.toString())
                    recyclerViewCommercial?.adapter = CommercialPropertiesAdapter(
                            activity!!,
                            arrData1,
                            this@CommercialFragment
                    )
                }else{

                }

            }
        })


    }

    override fun onPropertyTypeClick(PropertyType: String) {
        strPropetyFor = SharedPrefsConstant.TransactionType + " eq %27"+PropertyType+"%27 and "

    }

}