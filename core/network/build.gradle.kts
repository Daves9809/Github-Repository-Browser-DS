plugins {
    id("github.android.library")
    id("github.android.hilt")
    id("kotlinx-serialization")
    alias(libs.plugins.secrets)
    alias(libs.plugins.apollo.graph.ql)
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

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation(libs.apollo3)
    implementation(libs.paging)
}

apollo {
    service("service") {
        packageName.set("com.daves9809.github.core.network")
        generateOptionalOperationVariables.set(false)
    }
}
