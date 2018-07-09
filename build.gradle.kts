import org.gradle.jvm.tasks.Jar

group = "com.lohika.morning"
version = "0.0.1-SNAPSHOT"

val mainClass = "ua.kug.TwitterWallJSONKt"

plugins {
    application
    java
    kotlin("jvm") version "1.2.40"
}

application {
    mainClassName = mainClass
}

dependencies {
    val twitter4jVer = "4.0.6"
    val jacksonCoreVer = "2.9.2"

    compile(kotlin("stdlib-jdk8"))
    compile(kotlin("reflect"))
    compile("org.twitter4j", "twitter4j-core", twitter4jVer)
    compile("org.twitter4j", "twitter4j-stream", twitter4jVer)
    compile("io.javalin", "javalin", "1.3.0")
    compile("com.fasterxml.jackson.core", "jackson-core", jacksonCoreVer)
    compile("com.fasterxml.jackson.core", "jackson-databind", jacksonCoreVer)
    compile("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "0.22.2")
    testCompile(kotlin("test-junit"))
    testCompile("junit", "junit", "4.12")
    testCompile("io.kotlintest", "kotlintest", "2.0.7")
    testCompile("com.nhaarman", "mockito-kotlin", "1.5.0")
}

repositories {
    jcenter()
}

val fatJar = task("fatJar", type = Jar::class) {
    manifest {
        attributes["Implementation-Version"] = version
        attributes["Main-Class"] = mainClass
    }
    from(configurations.runtime.map({ if (it.isDirectory) it else zipTree(it) }))
    with(tasks["jar"] as CopySpec)
}


tasks {
    "build" {
        dependsOn(fatJar)
    }
}