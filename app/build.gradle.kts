plugins {
    id("com.android.application")
}

android {
    namespace = "app.daazi.aluno.appclientevipsqllite"
    compileSdk = 34

    defaultConfig {
        applicationId = "app.daazi.aluno.appclientevipsqllite"
        minSdk = 24
        targetSdk = 34
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

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //janelas de pop up customizados
    implementation("com.github.Shashank02051997:FancyAlertDialog-Android:0.3")

    //download de imagens na Internet
    implementation("com.squareup.picasso:picasso:2.8")
}