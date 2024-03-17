// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
    }
}

plugins {
    id("com.android.application") version "8.1.1" apply false
    // Add the dependency for the Google services Gradle plugin
    id ( "com.google.gms.google-services" ) version "4.4.0" apply false
}