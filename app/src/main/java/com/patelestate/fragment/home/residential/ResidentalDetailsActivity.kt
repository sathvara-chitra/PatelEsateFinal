package com.patelestate.fragment.home.residential

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.crownfantancy.ui.Retrofit.RetrofitClient
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.gson.JsonObject
import com.patelestate.Loader
import com.patelestate.R
import com.patelestate.activity.SendRequestActivity
import com.patelestate.adapter.DimensionAdapter
import com.patelestate.adapter.ViewPagerAdapter
import com.patelestate.base.BaseActivity
import com.patelestate.model.DimensionModel
import kotlinx.android.synthetic.main.activity_residental_details.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import kotlin.math.roundToInt


class ResidentalDetailsActivity : BaseActivity() {
    private var propertyUniqid: String = ""
    private var relScrollImages: RelativeLayout? = null
    private var relStat: RelativeLayout? = null
    private var btnScheduleCall: RelativeLayout? = null
    private var dotscount: Int = 0
    private var sliderDotspanel: LinearLayout? = null
    private var viewPager: ViewPager? = null
    private var toolbar: Toolbar? = null
    private var viewPagerAdapter: ViewPagerAdapter? = null
    private var arrImages: ArrayList<String>? = null
    private var tvStat: TextView? = null
    private var txtType: TextView? = null
    private var txtRate: TextView? = null
    private var txtAddress: TextView? = null
    private var txtBedroom: TextView? = null
    private var txtBathroom: TextView? = null
    private var txtArea: TextView? = null
    private var txtDescription: TextView? = null

    private var txtSale: TextView? = null
    private var txtPrice: TextView? = null
    private var txtHouse: TextView? = null
    private var txtTax: TextView? = null
    private var txtYear: TextView? = null
    private var txtParkingType: TextView? = null
    private var txtLandSize: TextView? = null
    private var txtFloor: TextView? = null
    private var txtNeighbour: TextView? = null
    private var txtPark: TextView? = null
    private var txtHeating: TextView? = null
    private var txtFirepace: TextView? = null
    private var txtFull: TextView? = null
    private var txtStyle: TextView? = null
    private var dimensionModel: DimensionModel? = null
    private var arrMain: ArrayList<DimensionModel> = ArrayList()
    private var arrBasement: ArrayList<DimensionModel> = ArrayList()
    private var arrUpper: ArrayList<DimensionModel> = ArrayList()
    private var arrLower: ArrayList<DimensionModel> = ArrayList()
    private var rv_main: RecyclerView? = null
    private var rv_basement: RecyclerView? = null
    private var rv_Upper: RecyclerView? = null
    private var rv_Lower: RecyclerView? = null
    private var txtMainLevel: TextView? = null
    private var txtbasementLevel: TextView? = null
    private var txtUpperLevel: TextView? = null
    private var txtLowerLevel: TextView? = null
    var loader: Loader? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_residental_details)
        init()
    }

    private fun init() {
        loader = Loader(this)
        txtSale = findViewById(R.id.txtSale)
        txtPrice = findViewById(R.id.txtPrice)
        txtHouse = findViewById(R.id.txtHouse)
        txtTax = findViewById(R.id.txtTax)
        txtYear = findViewById(R.id.txtYear)
        txtParkingType = findViewById(R.id.txtParkingType)
        txtLandSize = findViewById(R.id.txtLandSize)
        txtFloor = findViewById(R.id.txtFloor)
        txtNeighbour = findViewById(R.id.txtNeighbour)
        txtPark = findViewById(R.id.txtPark)
        txtHeating = findViewById(R.id.txtHeating)
        txtFirepace = findViewById(R.id.txtFirepace)
        txtFull = findViewById(R.id.txtFull)
        txtStyle = findViewById(R.id.txtStyle)
        rv_main = findViewById(R.id.rv_main)
        txtMainLevel = findViewById(R.id.txtMainLevel)
        txtbasementLevel = findViewById(R.id.txtbasementLevel)
        rv_basement = findViewById(R.id.rv_basement)
        txtUpperLevel = findViewById(R.id.txtUpperLevel)
        rv_Upper = findViewById(R.id.rv_Upper)
        txtLowerLevel = findViewById(R.id.txtLowerLevel)
        rv_Lower = findViewById(R.id.rv_Lower)

        tvStat = findViewById(R.id.tvStat)
        txtType = findViewById(R.id.txtType)
        txtRate = findViewById(R.id.txtRate)
        txtAddress = findViewById(R.id.txtAddress)
        txtBedroom = findViewById(R.id.txtBedroom)
        txtBathroom = findViewById(R.id.txtBathroom)
        txtArea = findViewById(R.id.txtArea)
        txtDescription = findViewById(R.id.txtDescription)
        relScrollImages = findViewById<RelativeLayout>(R.id.relScrollImages)
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        relStat = findViewById<RelativeLayout>(R.id.relStat)
        btnScheduleCall = findViewById<RelativeLayout>(R.id.btnScheduleCall)
        viewPager = findViewById<ViewPager>(R.id.mViewPager)
        sliderDotspanel = findViewById<LinearLayout>(R.id.SliderDots)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        relScrollImages!!.layoutParams.height =
            (getWidth() * 0.80).roundToInt() // image slide height
        val param = relStat!!.layoutParams as ViewGroup.MarginLayoutParams
        param.setMargins(0, (getWidth() * 0.50).roundToInt(), 0, 0)
        relStat!!.layoutParams = param
        propertyUniqid = intent.getStringExtra("ID")!!
        getCommercialDetails()
        //setOnclick listner
        toolbar!!.setNavigationOnClickListener {
            finish()
        }

        tabs.addTab(tabs.newTab().setText("Facility"))
        tabs.addTab(tabs.newTab().setText("Property Summary"))
        tabs.addTab(tabs.newTab().setText("Dimention"))
        tabs.addTab(tabs.newTab().setText("Listed by"))

        tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tabs.selectedTabPosition) {
                    0 -> {
                        llFacility.visibility = View.VISIBLE
                        llPropertySummary.visibility = View.GONE
                        llDimention.visibility = View.GONE
                        llListedBy.visibility = View.GONE
                    }
                    1 -> {
                        llFacility.visibility = View.GONE
                        llDimention.visibility = View.GONE
                        llListedBy.visibility = View.GONE
                        llPropertySummary.visibility = View.VISIBLE
                    }
                    2 -> {
                        llFacility.visibility = View.GONE
                        llPropertySummary.visibility = View.GONE
                        llDimention.visibility = View.VISIBLE
                        llListedBy.visibility = View.GONE
                    }
                    3 -> {
                        llFacility.visibility = View.GONE
                        llPropertySummary.visibility = View.GONE
                        llDimention.visibility = View.GONE
                        llListedBy.visibility = View.VISIBLE
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        //Default
        llFacility.visibility = View.VISIBLE
        llPropertySummary.visibility = View.GONE
        llDimention.visibility = View.GONE
        llListedBy.visibility = View.GONE

        btnScheduleCall!!.setOnClickListener {
            val mainIntent = Intent(this, SendRequestActivity::class.java)
            startActivity(mainIntent)
        }
    }

    private fun getCommercialDetails() {
        loader!!.show()
        val call: Call<JsonObject?> = RetrofitClient.getClient()
            .getDetails1("https://patelestateapi-dev.azurewebsites.net/api/ResidentialProperty/" + propertyUniqid)
        call!!.enqueue(object : Callback<JsonObject?> {

            override fun onFailure(call: Call<JsonObject?>, t: Throwable) {
                Log.e("error data", call.toString())
                loader!!.cancel()
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<JsonObject?>,
                response: retrofit2.Response<JsonObject?>
            ) {
                loader!!.cancel()
                arrImages = ArrayList()
                val RegisterResponse = response.body()!!
                Log.d("Dataaaaaaaaaaaa", response.body()!!.toString())
                val jobj = JSONObject(RegisterResponse.toString())
                Log.d("JsornArray", jobj.toString())
                txtType!!.setText(jobj.getString("transactionType"))
                txtRate!!.setText("$" + jobj.getString("price") + ".00")
                txtAddress!!.setText(jobj.getString("addressLine1"))
                txtArea!!.setText(jobj.getString("totalFinishedArea"))
                txtDescription!!.setText(jobj.getString("publicRemarks"))
                val jsonArray: JSONArray = jobj.getJSONArray("residentialImages")
                for (i in 0 until jsonArray.length()) {
                    var json_object: JSONObject? = null
                    json_object = jsonArray.get(i) as JSONObject?
                    arrImages!!.add(json_object!!.getString("photoPath"))
                }
                val jsonArrayDimension: JSONArray = jobj.getJSONArray("residentialRooms")

                for (i in 0 until jsonArrayDimension.length()) {
                    var json_object: JSONObject? = null
                    json_object = jsonArrayDimension.get(i) as JSONObject?
                    dimensionModel = DimensionModel(
                        json_object!!.getString("roomsId"), json_object!!.getString("type"),
                        json_object!!.getString("level"), json_object!!.getString("dimension")
                    )
                    Log.e("dimension", json_object.toString())
                    if (json_object.getString("level").equals("Main level")) {
                        arrMain!!.add(dimensionModel!!)
                        Log.e("level4", arrMain.get(0).level.toString())
                    }
                    if (json_object.getString("level").equals("Basement")) {
                        arrBasement!!.add(dimensionModel!!)
                    }
                    if (json_object.getString("level").equals("Upper Level")) {
                        arrUpper!!.add(dimensionModel!!)
                    }
                    if (json_object.getString("level").equals("Lower Level")) {
                        arrLower!!.add(dimensionModel!!)
                    }


                }

                if (arrMain.size > 0) {
                    txtMainLevel!!.visibility = View.VISIBLE
                    rv_main!!.visibility = View.VISIBLE
                    txtMainLevel!!.setText(arrMain.get(0).level)
                    Log.e("level1", arrMain.get(0).level.toString())
                    val adapter = DimensionAdapter(this@ResidentalDetailsActivity, arrMain)
                    rv_main!!.adapter = adapter
                }
                if (arrBasement.size > 0) {
                    txtbasementLevel!!.visibility = View.VISIBLE
                    rv_basement!!.visibility = View.VISIBLE
                    txtbasementLevel!!.setText(arrBasement.get(0).level)
                    Log.e("level2", arrMain.get(0).level.toString())
                    val adapter = DimensionAdapter(this@ResidentalDetailsActivity, arrBasement)
                    rv_basement!!.adapter = adapter
                }
                if (arrUpper.size > 0) {
                    txtUpperLevel!!.visibility = View.VISIBLE
                    rv_Upper!!.visibility = View.VISIBLE
                    txtUpperLevel!!.setText(arrUpper.get(0).level)
                    Log.e("level3", arrMain.get(0).level.toString())
                    val adapter = DimensionAdapter(this@ResidentalDetailsActivity, arrUpper)
                    rv_Upper!!.adapter = adapter
                }
                if (arrLower.size > 0) {
                    txtLowerLevel!!.visibility = View.VISIBLE
                    rv_Lower!!.visibility = View.VISIBLE
                    txtLowerLevel!!.setText(arrLower.get(0).level)
                    Log.e("level4", arrMain.get(0).level.toString())
                    val adapter = DimensionAdapter(this@ResidentalDetailsActivity, arrLower)
                    rv_Lower!!.adapter = adapter
                }

                viewPagerAdapter = ViewPagerAdapter(this@ResidentalDetailsActivity, arrImages!!)
                viewPager!!.adapter = viewPagerAdapter
                dotscount = viewPagerAdapter!!.count
                val dots = arrayOfNulls<ImageView?>(dotscount)

                for (i in dots.indices) {
                    dots[i] = ImageView(this@ResidentalDetailsActivity)
                    dots[i]!!.setImageDrawable(
                        ContextCompat.getDrawable(
                            applicationContext,
                            R.drawable.default_dot
                        )
                    )
                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    params.setMargins(8, 0, 8, 0)
                    sliderDotspanel!!.addView(dots[i], params)
                }
                dots[0]!!.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.active_dot
                    )
                )
                viewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                    }

                    override fun onPageSelected(position: Int) {
                        for (i in 0 until dotscount) {
                            dots[i]!!.setImageDrawable(
                                ContextCompat.getDrawable(
                                    applicationContext,
                                    R.drawable.default_dot
                                )
                            )
                        }
                        dots[position]!!.setImageDrawable(
                            ContextCompat.getDrawable(
                                applicationContext,
                                R.drawable.active_dot
                            )
                        )
                    }

                    override fun onPageScrollStateChanged(state: Int) {}
                })

                txtSale!!.setText(jobj.getString("transactionType"))
                txtPrice!!.setText(jobj.getString("transactionType"))
                txtHouse!!.setText(jobj.getString("transactionType"))
                txtTax!!.setText(jobj.getString("transactionType"))
                txtYear!!.setText(jobj.getString("transactionType"))
                txtParkingType!!.setText(jobj.getString("parking"))
                txtLandSize!!.setText(jobj.getString("transactionType"))
                txtFloor!!.setText(jobj.getString("flooringType"))
                txtNeighbour!!.setText(jobj.getString("transactionType"))
                txtPark!!.setText(jobj.getString("amenities"))
                txtHeating!!.setText(jobj.getString("heatingType"))
                txtFirepace!!.setText(jobj.getString("heatingFuel"))
                txtFull!!.setText(jobj.getString("basementType"))
                txtStyle!!.setText(jobj.getString("constructionStyleAttachment"))

            }
        })
//        val call: Call<JsonObject?> = RetrofitClient.getClient().getResidentialDetails()
//        call!!.enqueue(object : Callback<JsonObject?> {
//
//            override fun onFailure(call: Call<JsonObject?>, t: Throwable) {
//                Toast.makeText(this@ResidentalDetailsActivity, t.message, Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onResponse(
//                call: Call<JsonObject?>,
//                response: retrofit2.Response<JsonObject?>
//            ) {
//                arrImages = ArrayList()
//                val RegisterResponse = response.body()!!
//                Log.d("Dataaaaaaaaaaaa", response.body()!!.toString())
//                val jobj = JSONObject(RegisterResponse.toString())
//                Log.d("JsornArray", jobj.toString())
//                txtType!!.setText(jobj.getString("transactionType"))
//                txtRate!!.setText("$"+jobj.getString("price")+".00")
//                txtAddress!!.setText(jobj.getString("streetAddress"))
//                txtArea!!.setText(jobj.getString("totalFinishedArea"))
//                txtDescription!!.setText(jobj.getString("publicRemarks"))
//                val jsonArray: JSONArray = jobj.getJSONArray("residentialImages")
//                for (i in 0 until jsonArray.length()) {
//                    var json_object: JSONObject? = null
//                    json_object = jsonArray.get(i) as JSONObject?
//                    arrImages!!.add(json_object!!.getString("photoPath"))
//                }
//
//                viewPagerAdapter = ViewPagerAdapter(this@ResidentalDetailsActivity,arrImages!!)
//                viewPager!!.adapter = viewPagerAdapter
//                dotscount = viewPagerAdapter!!.count
//                val dots = arrayOfNulls<ImageView?>(dotscount)
//
//                for (i in dots.indices) {
//                    dots[i] = ImageView(this@ResidentalDetailsActivity)
//                    dots[i]!!.setImageDrawable(
//                        ContextCompat.getDrawable(
//                            applicationContext,
//                            R.drawable.default_dot
//                        )
//                    )
//                    val params = LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT
//                    )
//                    params.setMargins(8, 0, 8, 0)
//                    sliderDotspanel!!.addView(dots[i], params)
//                }
//                dots[0]!!.setImageDrawable(
//                    ContextCompat.getDrawable(
//                        applicationContext,
//                        R.drawable.active_dot
//                    )
//                )
//                viewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//                    override fun onPageScrolled(
//                        position: Int,
//                        positionOffset: Float,
//                        positionOffsetPixels: Int
//                    ) {
//                    }
//
//                    override fun onPageSelected(position: Int) {
//                        for (i in 0 until dotscount) {
//                            dots[i]!!.setImageDrawable(
//                                ContextCompat.getDrawable(
//                                    applicationContext,
//                                    R.drawable.default_dot
//                                )
//                            )
//                        }
//                        dots[position]!!.setImageDrawable(
//                            ContextCompat.getDrawable(
//                                applicationContext,
//                                R.drawable.active_dot
//                            )
//                        )
//                    }
//
//                    override fun onPageScrollStateChanged(state: Int) {}
//                })
//
//            }
//        })
    }
}