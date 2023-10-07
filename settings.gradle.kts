pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "GithubRepositoryBrowser"
include(":app")
include(":feature:home")
include(":feature:details")
include(":core:data")
include(":core:model")
include(":core:common")
include(":core:designsystem")
include(":core:network")
