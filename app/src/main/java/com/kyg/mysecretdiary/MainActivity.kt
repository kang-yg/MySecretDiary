package com.kyg.mysecretdiary

import android.content.Context
import android.os.Bundle
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import com.kyg.mysecretdiary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val MY_SHAREDPREFERENCES = "MY_SHAREDPREFERENCES"
    val PASSWORD_KEY = "PASSWORD"

    lateinit var activityMainBinding: ActivityMainBinding

    private val numberPicker1: NumberPicker by lazy {
        activityMainBinding.numberPicker1.apply {
            minValue = 0
            maxValue = 9
        }
    }

    private val numberPicker2: NumberPicker by lazy {
        activityMainBinding.numberPicker1.apply {
            minValue = 0
            maxValue = 9
        }
    }

    private val numberPicker3: NumberPicker by lazy {
        activityMainBinding.numberPicker1.apply {
            minValue = 0
            maxValue = 9
        }
    }

    private val openButton: AppCompatButton by lazy {
        activityMainBinding.openButton
    }

    private val changePasswordButton: AppCompatButton by lazy {
        activityMainBinding.changePasswordButton
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setOpenButton()
    }

    private fun setOpenButton() {
        openButton.setOnClickListener {
            val sharedPreference = getSharedPreferences(MY_SHAREDPREFERENCES, Context.MODE_PRIVATE)

            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"
            val passwordFromSharedPreference = sharedPreference.getString(PASSWORD_KEY, "000")

            if (passwordFromSharedPreference.equals(passwordFromUser)) {

            } else {
                AlertDialog.Builder(this).setTitle(resources.getString(R.string.fail))
            }
        }
    }
}