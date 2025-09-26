package com.example.careermate_app.ui

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.careermate_app.R

class JobDescriptionActivity : AppCompatActivity() {

    private lateinit var inputJobDescription: EditText
    private lateinit var btnAnalyzeJob: Button
    private lateinit var resultsTitle: TextView
    private lateinit var resultsContent: TextView
    private lateinit var btnMockInterview: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_description)

        inputJobDescription = findViewById(R.id.inputJobDescription)
        btnAnalyzeJob = findViewById(R.id.btnAnalyzeJob)
        resultsTitle = findViewById(R.id.resultsTitle)
        resultsContent = findViewById(R.id.resultsContent)
        btnMockInterview = findViewById(R.id.btnMockInterview)

        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        btnAnalyzeJob.setOnClickListener {
            val description = inputJobDescription.text.toString()

            if (description.isNotBlank()) {
                // Placeholder analysis logic
                resultsTitle.visibility = TextView.VISIBLE
                resultsContent.visibility = TextView.VISIBLE
                btnMockInterview.visibility = Button.VISIBLE

                resultsContent.text = """
                    • Skills: Java, SQL, Problem-solving
                    • Experience: 2+ years in software development
                    • Responsibilities: Build APIs, write unit tests
                    
                    ✅ Advice: Revise Java basics, prepare STAR-method answers, and practice coding problems.
                """.trimIndent()

                resultsContent.startAnimation(fadeIn)
                btnMockInterview.startAnimation(fadeIn)
            }
        }

        btnMockInterview.setOnClickListener {
            // Navigate to mock interview page (to be created)
            startActivity(Intent(this, MockInterviewActivity::class.java))
        }
    }
}