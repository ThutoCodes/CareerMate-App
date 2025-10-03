package com.example.careermate_app.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.careermate_app.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import okhttp3.*
// *** 1. IMPORT THE CORRECT EXTENSION FUNCTIONS ***
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class JobDescriptionActivity : AppCompatActivity() {

    private lateinit var inputJobDescription: TextInputEditText
    private lateinit var btnAnalyzeJob: MaterialButton
    private lateinit var loadingIndicator: ProgressBar
    private lateinit var resultsTitle: TextView
    private lateinit var resultsCard: MaterialCardView
    private lateinit var resultsContent: TextView
    private lateinit var btnMockInterview: MaterialButton
    private lateinit var backButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_description)

        // --- Initialize Views from the new layout ---
        inputJobDescription = findViewById(R.id.inputJobDescription)
        btnAnalyzeJob = findViewById(R.id.btnAnalyzeJob)
        loadingIndicator = findViewById(R.id.loadingIndicator)
        resultsTitle = findViewById(R.id.resultsTitle)
        resultsCard = findViewById(R.id.resultsCard)
        resultsContent = findViewById(R.id.resultsContent)
        btnMockInterview = findViewById(R.id.btnMockInterview)
        backButton = findViewById(R.id.backButton)

        // --- Click Listeners ---
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Go back to the previous screen
        }

        btnAnalyzeJob.setOnClickListener {
            val description = inputJobDescription.text.toString()
            if (description.isNotBlank()) {
                analyzeJob(description)
            }
        }

        btnMockInterview.setOnClickListener {
            // TODO: Navigate to MockInterviewActivity
            // startActivity(Intent(this, MockInterviewActivity::class.java))
        }
    }

    private fun analyzeJob(description: String) {
        showLoading(true)

        // Build JSON body
        val json = JSONObject()
        json.put("jobDescription", description)

        // *** 2. USE THE MODERN toRequestBody() METHOD ***
        val body = json.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        // Build request to backend
        val request = Request.Builder()
            .url("http://192.168.18.185:5000/analyze") // <-- PC IP address
            .post(body)
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    showLoading(false)
                    resultsContent.text = "❌ Network Request Failed: ${e.message}"
                    showResults(true) // Show results view even on failure
                }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread { showLoading(false) }
                val responseData = response.body?.string()

                if (!response.isSuccessful) {
                    runOnUiThread {
                        resultsContent.text = "⚠️ Server Error: ${response.message}\n$responseData"
                        showResults(true)
                    }
                } else {
                    try {
                        val jsonRes = JSONObject(responseData ?: "{}")
                        // Assuming your python backend sends a key like "analysis_results" or similar
                        val aiTips = jsonRes.optString("tips", "No analysis results found in response.")

                        runOnUiThread {
                            resultsContent.text = aiTips
                            showResults(false)
                        }
                    } catch (e: Exception) {
                        runOnUiThread {
                            resultsContent.text = "⚠️ Error parsing server response."
                            showResults(true)
                        }
                    }
                }
                response.close() // Ensure the response body is closed
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        loadingIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
        resultsCard.visibility = View.GONE // Hide previous results while loading
        resultsTitle.visibility = View.GONE
        btnMockInterview.visibility = View.GONE
        btnAnalyzeJob.isEnabled = !isLoading
    }

    private fun showResults(isError: Boolean) {
        resultsTitle.visibility = View.VISIBLE
        resultsCard.visibility = View.VISIBLE
        // Only show the mock interview button if the analysis was successful
        btnMockInterview.visibility = if (isError) View.GONE else View.VISIBLE
    }
}
