import com.sun.javafx.scene.CameraHelper.project
import io.ebean.gradle.EnhancePluginExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion = properties["kotlinVersion"] as String
val karibuVersion = properties["karibuVersion"] as String
val vaadin8Version = properties["vaadin8Version"] as String

buildscript {
  val kotlinVersion: String by project
  repositories {
    mavenCentral()
  }

  dependencies {
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    classpath("io.ebean:ebean-gradle-plugin:11.37.1")
  }
}

plugins {
  id("org.jetbrains.kotlin.jvm") version "1.3.31"
  id("org.gretty") version "2.3.1"
  id("com.devsoap.plugin.vaadin") version "1.4.1"
  war
}

repositories {
  mavenCentral()
}

configurations.all {
  resolutionStrategy.cacheChangingModulesFor(4, "hours")
}

defaultTasks("clean", "build")

group = "dep_endereco"
version = "1.0-SNAPSHOT"

apply(plugin = "war")
apply(plugin = "kotlin")
apply(plugin = "io.ebean")

gretty {
  contextPath = "/"
  servletContainer = "jetty9.4"
}

vaadin {
  version = "8.5.2"
}

configure<EnhancePluginExtension> {
  debugLevel = 0
}

val compileKotlin: KotlinCompile by tasks

compileKotlin.kotlinOptions {
  jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
  jvmTarget = "1.8"
}

tasks.named<Test>("test") {
  useJUnitPlatform()
}

dependencies {
  compile ("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  compile ("org.jetbrains.kotlin:kotlin-reflect")

  // logging
  // currently we are logging through the SLF4J API to LogBack. See logback.xml file for the logger configuration
  compile ("ch.qos.logback:logback-classic:1.2.3")
  compile ("org.slf4j:slf4j-api:1.7.25")
  // this will configure Vaadin to log to SLF4J
  compile ("org.slf4j:jul-to-slf4j:1.7.25")

  compile ("mysql:mysql-connector-java:5.1.40")
  compile ("org.imgscalr:imgscalr-lib:4.2")

  //Dependências do ebean
  //kapt "io.ebean:kotlin-querybean-generator:11.1.2")
  compile ("io.ebean:ebean:11.28.3")
//  compile ("io.ebean:kotlin-querybean-generator:11.19.1")
  compile ("io.ebean:querybean-generator:11.24.1")
  compile ("io.ebean:ebean-querybean:11.24.1")
  compile ("io.ebean:ebean-annotation:4.3")
  compile ("io.ebean.tools:finder-generator:4.2.2")
  //compile ("io.ebean:ebean-docker-run:1.5.1")

//  compile ("com.mchange:c3p0:0.9.5.2")
//  compile ("net.sf.ehcache:ehcache:2.10.4")
  compile ("org.sql2o:sql2o:1.6.0")
  compile ("net.sourceforge.jtds:jtds:1.3.1")
  compile ("org.apache.commons:commons-dbcp2:2.1.1")
  compile ("org.apache.commons:commons-pool2:2.4.2")
  compile ("org.imgscalr:imgscalr-lib:4.2")
  compile ("commons-codec:commons-codec:1.10")
  compile ("commons-io:commons-io:2.5")
  compile ("org.apache.commons:commons-lang3:3.5")

//View model
  compile ("net.sourceforge.dynamicreports:dynamicreports-core:6.0.0")
  compile ("net.sourceforge.dynamicreports:dynamicreports-adhoc:6.0.0")
  compile ("net.sourceforge.dynamicreports:dynamicreports-googlecharts:6.0.0")
// https://mvnrepository.com/artifact/com.lowagie/itext
  compile ("com.lowagie:itext:4.2.2")
  compile ("com.itextpdf:itextpdf:5.5.6")
// https://mvnrepository.com/artifact/org.eclipse.birt.runtime.3_7_1/com.lowagie.text
  compile ("org.eclipse.birt.runtime.3_7_1:com.lowagie.text:2.1.7")

  testCompile ("junit:junit:4.12")

  // logging
  // currently we are logging through the SLF4J API to LogBack. See logback.xml file for the logger configuration
  compile ("ch.qos.logback:logback-classic:1.2.3")
  compile ("org.slf4j:slf4j-api:1.7.25")
  // this will configure Vaadin to log to SLF4J
  compile ("org.slf4j:jul-to-slf4j:1.7.25")

  // workaround until https://youtrack.jetbrains.com/issue/IDEA-178071 is fixed
  compile ("com.vaadin:vaadin-themes:$vaadin8Version")
  compile ("com.vaadin:vaadin-push:$vaadin8Version")
  compile ("com.vaadin:vaadin-client-compiled:$vaadin8Version")
  providedCompile ("javax.servlet:javax.servlet-api:3.1.0")

  // REST
  compile ("org.jboss.resteasy:resteasy-servlet-initializer:3.1.3.Final")
  compile ("org.jboss.resteasy:resteasy-jackson2-provider:3.1.3.Final")

  //View
  compile ("com.github.mvysny.karibudsl:karibu-dsl-v8:$karibuVersion")
  compile ("com.google.guava:guava:23.0")
  compile ("com.google.inject:guice:4.1.0")
  compile ("javax.inject:javax.inject:1")
  compile ("tools.devnull:trugger:5.1.1")
  compile ("com.jarektoro:responsive-layout:2.0.0")
  compile ("org.vaadin:viritin:2.0.beta2")
  compile ("org.vaadin.addons:stepper:2.4.0")
  compile ("org.vaadin.addons:autocomplete:0.2.1")
  compile ("eu.maxschuster:vaadin-autocompletetextfield:3.0-alpha-2")
  compile ("org.vaadin.ui:numberfield:0.2.0")
  //compile ("com.vaadin:vaadin-context-menu:3.0.0")
  compile ("de.steinwedel.vaadin.addon:messagebox:4.0.21")
  compile ("org.vaadin.addons:extended-token-field:0.3.0")
  compile ("org.vaadin.crudui:crudui:2.3.0")
  compile ("org.vaadin.teemu:switch:3.0.0")
  compile ("com.vaadin.addon:vaadin-onoffswitch:1.1.0")
  compile ("org.vaadin.patrik:GridFastNavigation:2.2.4")

  compile ("org.vaadin.teemusa:flexlayout:0.1.0")
  compile ("com.github.markash:statistics-card:0.3.2")
  compile ("org.vaadin.patrik:GridFastNavigation:2.3.10")

  testCompile ("junit:junit:4.12")
}