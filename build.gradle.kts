plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

import java.net.URI

android {
    namespace = "com.erisdev.flymelive"
    compileSdk = 36

    defaultConfig {
        minSdk = 30

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        aidl = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components.findByName("android") ?: components.findByName("release"))
                
                groupId = "com.erisdev"
                artifactId = "flymelive"
                version = "1.0.0"
                
                pom {
                    name.set("FlymeLive")
                    description.set("一个用于简化 Flyme 系统实况通知功能使用的库")
                    url.set("https://github.com/wsu2059q/FlymeLive")
                    
                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    
                    developers {
                        developer {
                            id.set("wsu2059q")
                            name.set("WSu2059")
                            email.set("wsu2059@qq.com")
                        }
                    }
                    
                    scm {
                        connection.set("scm:git:git://github.com/wsu2059q/FlymeLive.git")
                        developerConnection.set("scm:git:ssh://github.com:wsu2059q/FlymeLive.git")
                        url.set("https://github.com/wsu2059q/FlymeLive/tree/main")
                    }
                }
            }
        }
        
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/wsu2059q/FlymeLive")
                credentials {
                    username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                    password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
                }
            }
            
            // 测试
            maven {
                name = "Local"
                url = uri("${project.layout.buildDirectory.get()}/repo")
            }
        }
    }
}