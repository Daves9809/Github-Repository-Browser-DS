plugins {
    id("github.android.library")
    id("github.android.hilt")
}

android {
    namespace = "com.daves9809.github.core.domain"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(project(":core:common"))
}