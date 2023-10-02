plugins {
    id("github.android.library")
    id("github.android.library.compose")
    id("github.android.hilt")
}

android {
    namespace = "com.daves9809.github.testing"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))
}