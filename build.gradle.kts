// Top-level build file where you can add configuration options common to all sub-projects/modules.


buildscript {
    val android_gradle_plugin_version by extra("7.2.2")
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.22")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.45")
    }

}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}