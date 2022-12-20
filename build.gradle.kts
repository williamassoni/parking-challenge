import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val mock_version: String by project
val kluent_version : String by project

plugins {
	kotlin("jvm") version "1.7.21"
	kotlin("plugin.spring") version "1.7.21"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
	testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.9.0")
	testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.0")

	testImplementation("io.mockk:mockk:$mock_version")
	testImplementation ("org.amshove.kluent:kluent:$kluent_version")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
	testLogging {
		events("passed","skipped","failed")
	}
}
