plugins {
    java
}

group = "io.x0a.layout-lang"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    implementation(project(":layout-lang"))
    implementation(kotlin("script-runtime", "1.3.30"))
    implementation(kotlin("script-util", "1.3.30"))
    implementation(kotlin("compiler-embeddable", "1.3.30"))
    runtime(kotlin("scripting-compiler-embeddable", "1.3.30"))
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
