package com.example.careermate_app.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.careermate_app.R
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.careermate_app.models.LoginRequest
import com.example.careermate_app.models.LoginResponse
import com.example.careermate_app.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LogInActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        // Grab references to UI elements
        val emailField = findViewById<EditText>(R.id.emailField)
        val passwordField = findViewById<EditText>(R.id.passwordField)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener{
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if(email.isNotEmpty() && password.isNotEmpty()){
                loginUser(email,password)

            }else{
                Toast.makeText(this,"Please fill all fields", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun loginUser(email:String, password:String){
        val request = LoginRequest(email,password)

        //Call API
        val call = ApiClient.apiService.loginUser(request)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful){
                    val loginResponse = response.body()
                    Toast.makeText(this@LogInActivity,"Welcome!",Toast.LENGTH_SHORT).show()
                    Log.d("API LOGIN","Message: ${loginResponse?.message}")


                    //Navigate to MainActivity
                    val intent = Intent(this@LogInActivity,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LogInActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                Log.e("API_LOGIN_ERROR",t.toString())
            }
        })





    }
}