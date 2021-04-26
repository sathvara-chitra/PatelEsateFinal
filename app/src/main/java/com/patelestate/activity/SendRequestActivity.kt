package com.patelestate.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.patelestate.R
import java.text.SimpleDateFormat
import java.util.*


class SendRequestActivity : AppCompatActivity() {
    private var rl_calendar: RelativeLayout? = null
    private var txtDate: TextView? = null
    private var btnSend: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_request)
        init()
        onCLick()
    }


    private fun init() {
        rl_calendar = findViewById(R.id.rl_calendar)
        txtDate = findViewById(R.id.txtDate)
        btnSend = findViewById(R.id.btnSend)
    }

    private fun onCLick() {
        rl_calendar!!.setOnClickListener {

            val mCalendar: Calendar = GregorianCalendar()
            mCalendar.setTime(Date())

            DatePickerDialog(
                this,
                R.style.my_dialog_theme,
                { view, year, monthOfYear, dayOfMonth ->
                    //do something with the date
                    val month_date = SimpleDateFormat("MMMM")
                    val date: String = dayOfMonth.toString() + " " +  month_date.format(monthOfYear) + " " + year
                    txtDate!!.setText(date)

                },
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        btnSend!!.setOnClickListener {
            Toast.makeText(this, "Send Request Successfully", Toast.LENGTH_SHORT).show()
        }

    }

}