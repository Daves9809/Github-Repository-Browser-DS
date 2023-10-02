plugins {
    id("github.android.library")
    id("github.android.hilt")
    id("kotlinx-serialization")
    alias(libs.plugins.secrets)
}

android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "com.daves9809.github.core.network"
}

secrets {
    defaultPropertiesFileName = "secrets.defaults.properties"
}

dependencies {

    implementation(project(":core:common"))
    implementation(project(":core:model"))

    testImplementation(project(":core:testing"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)

}