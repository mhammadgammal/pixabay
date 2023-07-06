plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs.kotlin")
}
tasks.register<Wrapper>("wrapper") {
    gradleVersion = "7.2"
}
android {
    namespace = "com.example.pixabay"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.pixabay"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures{
        @Incubating
        dataBinding = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }

}

dependencies {
    val roomVersion = "2.5.2"
    val navVersion = "2.5.2"
    val lifecycleVersion = "2.6.1"

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    //CoroutineWorker
    implementation("androidx.work:work-runtime-ktx:2.8.1")

    //Coil
    implementation("io.coil-kt:coil:2.4.0")
    //Room
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$roomVersion")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")
    //Navigation Component
    implementation ("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation ("androidx.navigation:navigation-ui-ktx:$navVersion")
    "androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion"

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")

    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")

    //ssp
    implementation("com.intuit.ssp:ssp-android:1.1.0")
    //sdp
    implementation("com.intuit.sdp:sdp-android:1.1.0")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    //Gson & Gson converter
    implementation ("com.google.code.gson:gson:2.9.1")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}