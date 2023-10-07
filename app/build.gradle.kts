@file:Suppress("UnstableApiUsage", "UNUSED_VARIABLE")

plugins {
    id("github.android.application")
    id("github.android.application.compose")
    id("github.android.hilt")
}

android {
    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        applicationId = "com.daves9809.github"
        versionCode = getVersionCode()
        versionName = getVersionName()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        val debug by getting {
            applicationIdSuffix = GithubRepositoryBrowserBuildType.DEBUG.applicationIdSuffix
        }
        val release by getting {
            isMinifyEnabled = true
            applicationIdSuffix = GithubRepositoryBrowserBuildType.RELEASE.applicationIdSuffix
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    namespace = "com.daves9809.github"
}

dependencies {
    implementation(project(":feature:home"))
    implementation(project(":feature:details"))

    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))

    androidTestImplementation(kotlin("test"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.junit4)
    implementation(libs.androidx.test.ext)
    implementation(libs.androidx.navigation.compose)

    debugImplementation(libs.androidx.compose.ui.tooling)
}

val checkReleaseVersion by tasks.registering {
    doLast {
        val versionName = android.defaultConfig.versionName
        if (versionName?.matches("\\d+(\\.\\d+)+".toRegex()) == false) {
            throw GradleException(
                "Version name for release builds can only be numeric (like 1.0.0), but was $versionName\n" +
                        "Please use git tag to set version name on the current commit and try again\n" +
                        "For example: git tag -a 1.0.0 -m 'tag message'"
            )
        }
    }
}