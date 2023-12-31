plugins {
    id("github.android.library")
    id("github.android.hilt")
}

android {
    namespace = "com.daves9809.github.core.model"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.junit)
}