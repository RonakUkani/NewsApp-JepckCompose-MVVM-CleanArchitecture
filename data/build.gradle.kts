plugins {
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.app.data"
    compileSdk = 34

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    defaultConfig {
        resValue("string", "apiUrl", project.findProperty("apiUrl").toString())
        resValue("string", "apiKey", project.findProperty("apiKey").toString())
    }
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit.coroutines.adapter)
    implementation(libs.koin.android)
    implementation(libs.okhttp3.logging.interceptor)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.arch.core.testing)
}
