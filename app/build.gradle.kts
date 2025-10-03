plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    //1. ADD THIS PLUGIN for Firebase
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.careermate_app"
    compileSdk = 34

    // You can keep this, but it's no longer used for the backend URL
    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.example.careermate_app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // 2. REMOVE THIS LINE - We are not using the old backend anymore
        // buildConfigField("String", "BACKEND_URL", "\"http://10.0.2.2:3000/api/\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.8.0")

    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("org.json:json:20230227")
    // 3. ADD FIREBASE DEPENDENCIES
    // Import the Bill of Materials (BoM) for Firebase, which manages library versions
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))

    // Add the dependency for Firebase Authentication
    implementation("com.google.firebase:firebase-auth-ktx")

    // Add the dependency for Firestore Database (to save user profiles)
    implementation("com.google.firebase:firebase-firestore-ktx")


    // 4. REMOVE OLD NETWORKING LIBRARIES (no longer needed for auth)
    // implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // implementation("com.squareup.okhttp3:okhttp:4.9.3")
    // implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
