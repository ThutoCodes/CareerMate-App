package com.example.careermate_app.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.careermate_app.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DashboardActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // --- Initialize Views ---
        drawerLayout = findViewById(R.id.drawer_layout)
        val settingsButton = findViewById<ImageButton>(R.id.settingsButton)
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        val welcomeUserText = findViewById<TextView>(R.id.welcomeUser)

        // Fetch user name
        fetchUserName(welcomeUserText)

        // Drawer logic
        settingsButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        setupDrawerContent(navigationView)

        // Buttons navigation
        findViewById<MaterialButton>(R.id.btnUploadResume).setOnClickListener {
            startActivity(Intent(this, UploadResumeActivity::class.java))
        }
        findViewById<MaterialButton>(R.id.btnJobDescriptionMatch).setOnClickListener {
            startActivity(Intent(this, JobDescriptionActivity::class.java))
        }
        findViewById<MaterialButton>(R.id.btnProgressTracker).setOnClickListener {
            // TODO: Add ProgressTrackerActivity
        }
    }

    override fun onStart() {
        super.onStart()

        // Animate logo and texts
        val logo = findViewById<ImageView>(R.id.appIcon)
        val title = findViewById<TextView>(R.id.welcomeTitle)
        val subtitle = findViewById<TextView>(R.id.welcomeUser)
        val btnUpload = findViewById<MaterialButton>(R.id.btnUploadResume)
        val btnJobMatch = findViewById<MaterialButton>(R.id.btnJobDescriptionMatch)
        val btnProgress = findViewById<MaterialButton>(R.id.btnProgressTracker)

        logo.animate().alpha(1f).translationY(0f).setDuration(600).setStartDelay(200).start()
        title.animate().alpha(1f).translationY(0f).setDuration(600).setStartDelay(400).start()
        subtitle.animate().alpha(1f).translationY(0f).setDuration(600).setStartDelay(600).start()
        btnUpload.animate().alpha(1f).translationY(0f).setDuration(600).setStartDelay(800).start()
        btnJobMatch.animate().alpha(1f).translationY(0f).setDuration(600).setStartDelay(1000).start()
        btnProgress.animate().alpha(1f).translationY(0f).setDuration(600).setStartDelay(1200).start()
    }

    private fun fetchUserName(welcomeUserText: TextView) {
        val user = Firebase.auth.currentUser
        if (user != null) {
            val db = Firebase.firestore
            db.collection("users").document(user.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val username = document.getString("username")
                        welcomeUserText.text = "Hello, ${username ?: "User"}!"
                    }
                }
                .addOnFailureListener {
                    welcomeUserText.text = "Hello, User!"
                }
        }
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        val darkModeItem = navigationView.menu.findItem(R.id.nav_dark_mode)
        val darkModeSwitch = darkModeItem.actionView?.findViewById<MaterialSwitch>(R.id.drawer_switch)

        darkModeSwitch?.isChecked =
            AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES

        darkModeSwitch?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_logout -> {
                    signOut()
                    true
                }
                else -> false
            }
        }
    }

    private fun signOut() {
        Firebase.auth.signOut()
        drawerLayout.closeDrawers()
        val intent = Intent(this, LogInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}