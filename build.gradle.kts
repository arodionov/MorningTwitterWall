import org.gradle.api.tasks.bundling.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "ua.kug"
version = "1.0-SNAPSHOT"

plugins {
    val kotlinVer = "1.2.21"

    kotlin("plugin.allopen") version kotlinVer
    kotlin("plugin.spring") version kotlinVer
    kotlin("jvm") version kotlinVer
}

buildscript {

    repositories {
        jcenter()
    }

    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:1.5.4.RELEASE")
    }
}

tasks.withType<Jar> {
    baseName = "morning-demo"
}

apply {
    plugin("org.springframework.boot")
}

dependencies {
    val twitter4jVer = "4.0.6"

    compile(kotlin("stdlib-jdk8"))
    compile(kotlin("reflect"))
    compile("org.twitter4j", "twitter4j-core", twitter4jVer)
    compile("org.twitter4j", "twitter4j-stream", twitter4jVer)
    compile("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "0.22.2")
    compile("org.springframework.boot", "spring-boot-starter-web")
    testCompile(kotlin("test-junit"))
    testCompile("junit", "junit", "4.12")
    testCompile("io.kotlintest", "kotlintest", "2.0.7")
    testCompile("com.nhaarman", "mockito-kotlin", "1.5.0")
    testCompile("org.springframework.boot", "spring-boot-starter-test")
}

configure<JavaPluginConvention> {
    setSourceCompatibility(1.8)
    setTargetCompatibility(1.8)
}

repositories {
    jcenter()
}