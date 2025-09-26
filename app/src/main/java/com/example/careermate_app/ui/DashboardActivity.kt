package com.example.careermate_app.ui

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.careermate_app.R

class DashboardActivity : AppCompatActivity() {

    private lateinit var btnUploadResume: Button
    private lateinit var btnJobDescriptionMatch: Button
    private lateinit var btnProgressTracker: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Initialize buttons
        btnUploadResume = findViewById(R.id.btnUploadResume)
        btnJobDescriptionMatch = findViewById(R.id.btnJobDescriptionMatch)
        btnProgressTracker = findViewById(R.id.btnProgressTracker)

        // Load animations
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)

        // Apply animations
        btnUploadResume.startAnimation(fadeIn)
        btnJobDescriptionMatch.startAnimation(slideUp)
        btnProgressTracker.startAnimation(slideUp)

        // Navigation
        btnUploadResume.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_click))
            startActivity(Intent(this, UploadResumeActivity::class.java))
        }

        btnJobDescriptionMatch.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_click))
            startActivity(Intent(this, JobDescriptionActivity::class.java))
        }

        btnProgressTracker.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_click))
            startActivity(Intent(this, ProgressTrackerActivity::class.java))
        }
    }
}