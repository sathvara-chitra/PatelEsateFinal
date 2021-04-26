package com.patelestate.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.crownfantancy.ui.Retrofit.RetrofitClient
import com.google.gson.JsonArray
import com.patelestate.R
import com.patelestate.adapter.ResidencialPropertiesAdapter
import com.patelestate.base.BaseActivity
import com.patelestate.model.Residential.GetResidentialList
import com.patelestate.model.Residential.GetResidentialListItem
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback


class LoginActivity : BaseActivity() {
    private var arrData1: ArrayList<GetResidentialListItem> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        getHeroes()
    }

    fun openSignup(view: View) {
        finish()
    }

    fun actionLogin(view: View) {
        val mIntent = Intent(this, HomeActivity::class.java)
        startActivity(mIntent)
        overridePendingTransition(0, 0)
        finish()
    }

    fun actionForget(view: View) {
        val mIntent = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(mIntent)
        overridePendingTransition(0, 0)
        finish()
    }

    private fun getHeroes() {
//        val call: Call<List<GetResidentialList?>?>? = RetrofitClient.getClient().getHeroes1()
//        call!!.enqueue(object : Callback<List<GetResidentialList?>?> {
//
//            override fun onFailure(call: Call<List<GetResidentialList?>?>, t: Throwable) {
//                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onResponse(
//                call: Call<List<GetResidentialList?>?>,
//                response: retrofit2.Response<List<GetResidentialList?>?>
//            ) {
//                val RegisterResponse = response.body()!!
//                Log.e("Data",RegisterResponse.toString())
//                Log.e("Data","Done")
//
//            }
//        })
        val call: Call<GetResidentialList> = RetrofitClient.getClient().getResedentialProperty1("https://patelestateapi-dev.azurewebsites.net/api/ResidentialProperty?%24top=12&%24skip=12")
        call!!.enqueue(object : Callback<GetResidentialList> {

            override fun onFailure(call: Call<GetResidentialList>, t: Throwable) {
                Log.e("error data", call.toString())
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<GetResidentialList>,
                response: retrofit2.Response<GetResidentialList>
            ) {
//                val RegisterResponse = response!!.body()!!
                Log.e("Data", "Done")



//                val jsonArray = JSONArray(RegisterResponse)
                Log.e("Data", response.toString())

            }
        })

    }

//    private fun getHeroes() {
//
//        val call = RetrofitClient.getClient().getHeroes()
//
//
//        call.enqueue(object : Callback<GetResidentialList> {
//
//
//
//            override fun onFailure(call: Call<GetResidentialList>, t: Throwable) {
//                Log.d("Error", call.toString())
//            }
//
//            override fun onResponse(
//                call: Call<GetResidentialList>,
//                response: retrofit2.Response<GetResidentialList>
//            ) {
//                val RegisterResponse = response.body()!!
//                println("Get Resister Response: " + response.body().toString())
//                val res: String = response.body().toString()
//                val message: String? = response.message()
//                Log.d("Json Response", res)
//                Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG).show()
//
////                        arrData1.addAll(response.body()!!.size())
//            }
//
//
//        })
//    }
}

