@file:Suppress("UnstableApiUsage")

rootProject.name = "Battleship"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

val projectNamespace: String by settings
settings.extra["namespace"] = projectNamespace

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":app")
include(":server")
include(":core:ui")
include(":core:data")
include(":core:domain")
include(":feature:menu")
include(":feature:settings")
include(":feature:play")
include(":feature:config")
include(":feature:leaderboard")
include(":data:game")
include(":data:settings")
include(":domain:game")
include(":domain:settings")