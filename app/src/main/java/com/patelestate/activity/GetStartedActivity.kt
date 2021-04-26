package com.patelestate.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.patelestate.R
import com.patelestate.base.BaseActivity

class GetStartedActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)
    }

    fun signUpWithEmail(view: View) {
        val mainIntent = Intent(this, SignUpActivity::class.java)
        startActivity(mainIntent)
        overridePendingTransition(0, 0)
    }

    fun signUpWithFacebook(view: View) {}
    fun signUpWithGoogle(view: View) {}
    fun openLogin(view: View) {
        val mIntent = Intent(this, LoginActivity::class.java)
        startActivity(mIntent)
        overridePendingTransition(0, 0)
    }

    fun goToHome(view: View) {
        val mIntent = Intent(this, HomeActivity::class.java)
//        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(mIntent)
        overridePendingTransition(0, 0)
        finish()
    }
}