package com.patelestate.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.patelestate.R
import com.patelestate.base.BaseActivity

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
    }

    fun openLogin(view: View) {
        val mIntent = Intent(this, LoginActivity::class.java)
        startActivity(mIntent)
        overridePendingTransition(0, 0)
        finish()
    }
}