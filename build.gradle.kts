plugins {
    application
    kotlin("jvm") version "1.2.21"
}

application {
    mainClassName = "ua.kug.TwitterWallJSONKt"
}

val twitter4jVer = "4.0.6"
val jacksoCoreVer = "2.9.2"

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile(kotlin("reflect"))
    compile("org.twitter4j", "twitter4j-core", twitter4jVer)
    compile("org.twitter4j", "twitter4j-stream", twitter4jVer)
    compile("io.javalin", "javalin", "1.3.0")
    compile("com.fasterxml.jackson.core", "jackson-core", jacksoCoreVer)
    compile("com.fasterxml.jackson.core", "jackson-databind", jacksoCoreVer)
    compile("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "0.22.2")
    compile("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "0.22.2")
    testCompile("org.jetbrains.kotlin", "kotlin-test-junit")
    testCompile("junit", "junit", "4.12")
    testCompile("io.kotlintest", "kotlintest", "2.0.1")
    testCompile("com.nhaarman", "mockito-kotlin", "0.9.0")

}

repositories {
    jcenter()
}
