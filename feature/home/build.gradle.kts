plugins {
    id("github.android.feature")
    id("github.android.library.compose")
}

android {
    namespace = "com.daves9809.github.feature.home"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:data"))

    implementation(libs.timber)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.compose.material3)
    implementation(libs.paging)
    implementation(libs.paging.compose)
}