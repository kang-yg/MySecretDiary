package com.kyg.mysecretdiary

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MotionEvent
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import com.kyg.mysecretdiary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val MY_SHAREDPREFERENCES = "MY_SHAREDPREFERENCES"
    private val PASSWORD_KEY = "PASSWORD"

    lateinit var activityMainBinding: ActivityMainBinding

    private val numberPicker1: NumberPicker by lazy {
        activityMainBinding.numberPicker1.apply {
            minValue = 0
            maxValue = 9
        }
    }

    private val numberPicker2: NumberPicker by lazy {
        activityMainBinding.numberPicker2.apply {
            minValue = 0
            maxValue = 9
        }
    }

    private val numberPicker3: NumberPicker by lazy {
        activityMainBinding.numberPicker3.apply {
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

    private val sharedPreference: SharedPreferences by lazy {
        getSharedPreferences(MY_SHAREDPREFERENCES, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        setNumberPicker()
        setOpenButton()
        setChangePasswordButton()
    }

    private fun setNumberPicker() {
        numberPicker1
        numberPicker2
        numberPicker3
    }

    private fun setOpenButton() {
        openButton.setOnClickListener {
            if (changePasswordButton.isPressed) {
                // 비밀번호 변경 중인경우
                showToast(this, resources.getString(R.string.PASSWORD_CHANGING));

                return@setOnClickListener
            } else {
                if (checkPassword()) {
                    // 비밀번호가 맞은 경우
                    startActivity(Intent(this, DiaryActivity::class.java))
                } else {
                    // 비밀번호가 틀린 경우
                    val titleFromResource = resources.getString(R.string.PASSWORD_FAIL_TITLE)
                    val messageFromResource = resources.getString(R.string.PASSWORD_FAIL_CONTENT)
                    showAlertDialog(titleFromResource, messageFromResource)
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setChangePasswordButton() {
        changePasswordButton.setOnTouchListener { _, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (!changePasswordButton.isPressed) {
                        // 비밀번호 변경이 활성화된 상태
                        if (checkPassword()) {
                            changePasswordButton.isPressed = !changePasswordButton.isPressed
                        } else {
                            val titleFromResource =
                                resources.getString(R.string.PASSWORD_FAIL_TITLE)
                            val messageFromResource =
                                resources.getString(R.string.PASSWORD_FAIL_CONTENT)
                            showAlertDialog(titleFromResource, messageFromResource)
                        }
                    } else {
                        // 비밀번호 변경이 활성화된 상태
                        sharedPreference.edit().putString(
                            PASSWORD_KEY,
                            "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"
                        ).apply()
                        changePasswordButton.isPressed = !changePasswordButton.isPressed
                    }

                    return@setOnTouchListener true
                }

                else -> {
                    return@setOnTouchListener true
                }
            }
        }
    }

    private fun checkPassword(): Boolean {
        val passwordFromUser =
            "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"
        val passwordFromSharedPreference =
            sharedPreference.getString(PASSWORD_KEY, "000")

        return (passwordFromSharedPreference.equals(passwordFromUser))
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showAlertDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(resources.getString(R.string.PASSWORD_POSITIVE)) { _, _ -> }
            .create().show()
    }
}