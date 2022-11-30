package com.example.activitiesappfigma.ui

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.setFragmentResult
import java.text.SimpleDateFormat
import java.util.*

class DatePicker: DialogFragment(), DatePickerDialog.OnDateSetListener {
    private val calendar = Calendar.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        return DatePickerDialog(requireActivity(), this, year,  month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.YEAR, year)
        val selectDate = SimpleDateFormat("dd-MM-yyyy", Locale.GERMAN).format(calendar.time)
        val selectDateBundle = Bundle()
        selectDateBundle.putString("SELECTED_DATE", selectDate)
        setFragmentResult("REQUEST_KEY", selectDateBundle)
    }
}