import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`
    kotlin("jvm") version "2.0.0"
    kotlin("kapt") version "2.0.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

allprojects {
    group = "gg.solara.uhc.minis"
    version = "1.0.0"

    repositories {
        mavenCentral()
        configureScalaRepository()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "maven-publish")

    dependencies {
        compileOnly(kotlin("stdlib"))

        compileOnly("gg.scala.commons:bukkit:3.5.2")
        compileOnly("gg.scala.store:spigot:0.1.8")
        compileOnly("gg.scala.spigot:server:1.1.0")

        compileOnly("gg.scala.lemon:bukkit:1.8.3")
        compileOnly("gg.scala.cloudsync:spigot:1.0.1")
    }

    kotlin {
        jvmToolchain(jdkVersion = 17)
    }

    tasks.withType<ShadowJar> {
        archiveClassifier.set("")
        exclude(
            "**/*.kotlin_metadata",
            "**/*.kotlin_builtins",
            "META-INF/"
        )

        archiveFileName.set(
            "uhc-mini-${project.name}.jar"
        )
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.javaParameters = true
        kotlinOptions.jvmTarget = "17"
    }

    publishing {
        repositories.configureScalaRepository(dev = false)

        publications {
            register(
                name = "mavenJava",
                type = MavenPublication::class,
                configurationAction = shadow::component
            )
        }
    }

    tasks.getByName("build")
        .dependsOn(
            "shadowJar",
            "publishMavenJavaPublicationToScalaRepository"
        )
}

fun RepositoryHandler.configureScalaRepository(dev: Boolean = false)
{
    maven("${property("artifactory_contextUrl")}/gradle-${if (dev) "dev" else "release"}") {
        name = "scala"
        credentials {
            username = property("artifactory_user").toString()
            password = property("artifactory_password").toString()
        }
    }
}
