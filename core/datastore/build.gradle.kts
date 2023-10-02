plugins {
    id("github.android.library")
    id("github.android.hilt")
}

android {
    namespace = "com.daves9809.github.core.datastore"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))


    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.androidx.dataStore.core)

    implementation(libs.kotlinx.serialization.json)
}