package com.patelestate.fragment.home.commercial

import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.crownfantancy.ui.Retrofit.RetrofitClient
import com.google.gson.JsonObject
import com.patelestate.Loader
import com.patelestate.R
import com.patelestate.adapter.ViewPagerAdapter
import com.patelestate.base.BaseActivity
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import kotlin.math.roundToInt


class CommercialDetailsActivity : BaseActivity() {
    private var viewPagerAdapter: ViewPagerAdapter?=null
    private var relScrollImages: RelativeLayout? = null
    private var relStat: RelativeLayout? = null
    private var dotscount: Int = 0
    private var propertyUniqid: String = ""
    private var sliderDotspanel: LinearLayout? = null
    private var viewPager: ViewPager? = null
    private var toolbar: Toolbar? = null
    private var tvStat: TextView? = null
    private var txtType: TextView? = null
    private var txtRate: TextView? = null
    private var txtAddress: TextView? = null
    private var txtStatus: TextView? = null
    private var txtArea: TextView? = null
    private var txtDescription: TextView? = null
    private  var arrImages:ArrayList<String>? = null
    var loader: Loader? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commercial_details)
        init()
    }

    private fun init() {
        loader = Loader(this)
        tvStat = findViewById(R.id.tvStat)
        txtType = findViewById(R.id.txtType)
        txtRate = findViewById(R.id.txtRate)
        txtAddress = findViewById(R.id.txtAddress)
        txtStatus = findViewById(R.id.txtStatus)
        txtArea = findViewById(R.id.txtArea)
        txtDescription = findViewById(R.id.txtDescription)
        relScrollImages = findViewById<RelativeLayout>(R.id.relScrollImages)
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        relStat = findViewById<RelativeLayout>(R.id.relStat)
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
    }

    private fun getCommercialDetails() {
        loader!!.show()
        val call: Call<JsonObject?> = RetrofitClient.getClient().getDetails1("https://patelestateapi-dev.azurewebsites.net/api/CommercialProperties/"+propertyUniqid)
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
//                val RegisterResponse = response.body()!!
                arrImages = ArrayList()
                val RegisterResponse = response.body()!!
                Log.d("Dataaaaaaaaaaaa", response.body()!!.toString())
                val jobj = JSONObject(RegisterResponse.toString())
                Log.d("JsornArray", jobj.toString())
                txtType!!.setText(jobj.getString("transactionType"))
                txtRate!!.setText("$"+jobj.getString("askingprice")+".00")
                txtAddress!!.setText(jobj.getString("address"))
                txtStatus!!.setText(jobj.getString("status"))
                txtArea!!.setText(jobj.getString("landArea"))
                txtDescription!!.setText(jobj.getString("description"))
                val jsonArray: JSONArray = jobj.getJSONArray("commercialImages")
                for (i in 0 until jsonArray.length()) {
                    var json_object: JSONObject? = null
                    json_object = jsonArray.get(i) as JSONObject?
                    arrImages!!.add(json_object!!.getString("photoPath"))
                }

                viewPagerAdapter = ViewPagerAdapter(this@CommercialDetailsActivity,arrImages!!)
                viewPager!!.adapter = viewPagerAdapter
                dotscount = viewPagerAdapter!!.count
                val dots = arrayOfNulls<ImageView?>(dotscount)

                for (i in dots.indices) {
                    dots[i] = ImageView(this@CommercialDetailsActivity)
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


            }
        })

    }


}