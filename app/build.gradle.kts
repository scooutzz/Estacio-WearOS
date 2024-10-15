plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.missao3"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.missao3"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

//    implementation(libs.play.services.wearable)
//    implementation(libs.appcompat)
//    implementation(libs.material)
//    implementation(libs.activity)
//    implementation(libs.constraintlayout)
    implementation("com.google.android.gms:play-services-wearable:18.0.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.activity:activity:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    implementation("androidx.wear:wear:1.2.0")
    implementation("androidx.media:media:1.2.1")
}