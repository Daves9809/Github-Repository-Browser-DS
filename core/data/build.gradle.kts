plugins {
    id("github.android.library")
    id("github.android.hilt")
}

android {
    namespace = "com.daves9809.github.core.data"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    testImplementation(libs.junit4)

    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}