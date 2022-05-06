package com.jsquaredstudios.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var tvSelectedDate: TextView? = null
    private var tvAgeInMinutes: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvSelectedDate = findViewById(R.id.tvDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeMinutes)
        val buttonDatePicker: Button = findViewById(R.id.btSelectDate)
        buttonDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                /* Getting the Selected Date in Milliseconds */
                val theDate = sdf.parse(selectedDate)
                /* Converting the Selected Date Into Minutes */
                theDate?.let {
                    val selectedDateInMinutes = theDate.time / 60000
                    /* Getting the Current Date based on the Phones Information */
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    /* Converting the Current Date Into Minutes */
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000
                        /* Calculating the Difference in Minutes to get the Age in Minutes */
                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        tvAgeInMinutes?.text = differenceInMinutes.toString()
                    }
                }

                tvSelectedDate?.text = selectedDate

            },
            year,
            month,
            day
        )
        /*Making Max Date The Day Before Current Date*/
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()

    }
}