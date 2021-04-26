package com.patelestate.activity

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.patelestate.R
import com.patelestate.base.BaseActivity


class ForgotPasswordActivity : BaseActivity() {

    private var btnSubmit: TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        init()
    }

    private fun init() {
        btnSubmit = findViewById(R.id.btnSubmit)as TextView

        //set click listner
        btnSubmit!!.setOnClickListener {
            showAlertDialogButtonClicked(it)
        }
    }


    fun showAlertDialogButtonClicked(view: View?) {

        // Create an alert builder
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)

        // set the custom layout
        val customLayout: View = layoutInflater
            .inflate(
                R.layout.forget_alertdialog,
                null
            )

        builder.setView(customLayout)
        // create and show
        // the alert dialog
        val dialog: android.app.AlertDialog? = builder.create()
        dialog!!.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow()!!.setBackgroundDrawableResource(R.drawable.white_rectangle_fill);
        // add a button

                    // AlertDialog to the Activity
                    val btnOkay = customLayout.findViewById<TextView>(R.id.btnOkay)
                    btnOkay.setOnClickListener {
                        dialog.dismiss()
                    }


        dialog!!.show()
    }
}