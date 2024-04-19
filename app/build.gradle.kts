plugins {
    id("com.android.application")
    // Add the Google services Gradle plugin
    id ( "com.google.gms.google-services" )
}

android {
    namespace = "com.example.appshop"
    compileSdk = 34

    dataBinding {
        enable=true
    }

    viewBinding {
        enable=true
    }

    defaultConfig {
        applicationId = "com.example.appshop"
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    //Depency mặc định
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Import the Firebase BoM
    implementation ( platform ( "com.google.firebase:firebase-bom:32.6.0" ))
    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation ( "com.google.firebase:firebase-analytics" )
    // Add the dependency for the Realtime Database library
    implementation("com.google.firebase:firebase-database")
    // Add the dependency for the Firebase Authentication library
    implementation("com.google.firebase:firebase-auth")
    // Add dependency for Cloud Firestore in Firebase
    implementation("com.google.firebase:firebase-firestore:24.9.1")
    // FirebaseUI for Cloud Firestore
    implementation("com.firebaseui:firebase-ui-firestore:8.0.2")
    //noinspection GradleCompatible
    implementation("com.android.support:design:28.0.0")
    implementation("android.arch.core:runtime:1.1.1")


    //Add libary for RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    //Add libary for CardView
    implementation("androidx.cardview:cardview:1.0.0")
    //Add libary for CircleImageView
    implementation("de.hdodenhof:circleimageview:3.1.0")
    //Add libary for Glide to show image with url
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
    //Add libary for ViewPager
    implementation("androidx.viewpager:viewpager:1.0.0")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    //Add libary for Picasso ( use for retriev image from firestore with string of url)
    implementation("com.squareup.picasso:picasso:2.71828")
    //Add libary RetroFit to call API
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
}