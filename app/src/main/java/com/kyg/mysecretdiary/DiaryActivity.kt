package com.kyg.mysecretdiary

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.kyg.mysecretdiary.databinding.ActivityDiaryBinding

class DiaryActivity : AppCompatActivity() {
    private val MY_SHAREDPREFERENCES = "MY_SHAREDPREFERENCES"
    private val DIARY_KEY = "DIARY"

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var activityDiaryBinding: ActivityDiaryBinding

    private val etDiary by lazy {
        activityDiaryBinding.etDiary
    }

    private val runnable by lazy {
        Runnable {
            etDiary.addTextChangedListener {
                sharedPreference.edit().putString(DIARY_KEY, etDiary.text.toString()).apply()
            }
        }
    }

    private val sharedPreference by lazy {
        getSharedPreferences(MY_SHAREDPREFERENCES, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDiaryBinding = DataBindingUtil.setContentView(this, R.layout.activity_diary)
        initViews()
        etDiary.setText(sharedPreference.getString(DIARY_KEY, ""))
    }

    private fun initViews() {
        setTextChangeListener()
    }

    @SuppressLint("CommitPrefEdits")
    fun setTextChangeListener() {
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, 500)
    }
}