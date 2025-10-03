package com.example.careermate_app.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.careermate_app.R
import com.google.android.material.card.MaterialCardView

class UploadResumeActivity : AppCompatActivity() {

    private lateinit var uploadBox: MaterialCardView
    private lateinit var btnAnalyzeResume: Button
    private lateinit var tvSelectedFileName: TextView
    private lateinit var backButton: ImageButton
    private var selectedFileUri: Uri? = null

    companion object {
        private const val PICK_FILE_REQUEST = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_resume)

        // Bind views
        uploadBox = findViewById(R.id.uploadBox)
        btnAnalyzeResume = findViewById(R.id.btnAnalyzeResume)
        tvSelectedFileName = findViewById(R.id.tvSelectedFileName)
        backButton = findViewById(R.id.backButton)

        // Disable analyze button until a file is selected
        btnAnalyzeResume.isEnabled = false

        // Back button functionality
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed() // goes back to previous screen
        }

        // File Picker
        uploadBox.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*" // Or "application/pdf" to restrict to PDF only
            startActivityForResult(
                Intent.createChooser(intent, "Select Resume"),
                PICK_FILE_REQUEST
            )
        }

        // Analyze Resume button
        btnAnalyzeResume.setOnClickListener {
            if (selectedFileUri != null) {
                // Animate button for feedback
                btnAnalyzeResume.animate().apply {
                    duration = 200
                    scaleX(0.95f)
                    scaleY(0.95f)
                    withEndAction {
                        btnAnalyzeResume.animate().scaleX(1f).scaleY(1f).start()
                        Toast.makeText(
                            this@UploadResumeActivity,
                            "Analyzing Resume...",
                            Toast.LENGTH_SHORT
                        ).show()
                        // TODO: Call AI/Server for analysis here
                    }
                }.start()
            } else {
                Toast.makeText(this, "Please select a file first", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE_REQUEST && resultCode == Activity.RESULT_OK) {
            selectedFileUri = data?.data
            val fileName = selectedFileUri?.lastPathSegment ?: "Unknown file"

            // Show filename inside card
            tvSelectedFileName.text = fileName

            // Enable Analyze button
            btnAnalyzeResume.isEnabled = true

            Toast.makeText(this, "File Selected: $fileName", Toast.LENGTH_SHORT).show()
        }
    }
}