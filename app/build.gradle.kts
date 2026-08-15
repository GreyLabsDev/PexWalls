plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.ksp)
}

android {
    compileSdk = 37
    namespace = "com.greylabsdev.pexwalls"

    defaultConfig {
        applicationId = "com.greylabsdev.pexwalls"
        minSdk = 30
        targetSdk = 36
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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

configurations {
    create("ktlint")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.espresso)

    add("ktlint", libs.ktlint)

    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)

    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    implementation(libs.okhttp.logging)

    implementation(libs.glide)

    implementation(libs.androidx.lifecycle.extensions)
    ksp(libs.androidx.lifecycle.compiler)

    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.timber)
}

tasks.register<JavaExec>("ktlint") {
    group = "ktlint"
    description = "Check Kotlin code style."
    classpath = configurations["ktlint"]
    mainClass.set("com.pinterest.ktlint.Main")
    args("src/**/*.kt")
}

tasks.named("check") {
    dependsOn(tasks.named("ktlint"))
}

tasks.register<JavaExec>("ktlintFormat") {
    group = "ktlint"
    description = "Fix Kotlin code style deviations."
    classpath = configurations["ktlint"]
    mainClass.set("com.pinterest.ktlint.Main")
    args("-F", "src/**/*.kt")
}
