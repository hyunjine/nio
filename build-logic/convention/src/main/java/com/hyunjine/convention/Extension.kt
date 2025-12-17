package com.hyunjine.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import java.util.Optional

internal fun DependencyHandlerScope.implementation(path: Optional<*>) {
    add("implementation", path.get())
}

internal fun DependencyHandlerScope.implementation(dependency: Any) {
    add("implementation", dependency)
}

internal fun DependencyHandlerScope.implementation(path: ProjectDependency) {
    add("implementation", path)
}

internal fun DependencyHandlerScope.compileOnly(path: ProjectDependency) {
    add("compileOnly", path)
}

internal fun DependencyHandlerScope.testImplementation(path: Optional<*>) {
    add("testImplementation", path.get())
}

internal fun DependencyHandlerScope.androidTestImplementation(path: Optional<*>) {
    add("androidTestImplementation", path.get())
}

internal fun DependencyHandlerScope.androidTestImplementation(dependency: Dependency) {
    add("androidTestImplementation", dependency)
}

internal fun DependencyHandlerScope.debugImplementation(path: Optional<*>) {
    add("debugImplementation", path.get())
}

internal fun DependencyHandlerScope.ksp(path: Optional<*>) {
    add("ksp", path.get())
}

internal fun DependencyHandlerScope.ksp(path: ProjectDependency) {
    add("ksp", path)
}

internal fun DependencyHandlerScope.kapt(path: Optional<*>) {
    add("kapt", path.get())
}

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

@JvmName("default")
internal fun Project.androidModule(block: CommonExtension<*, *, *, *, *, *>.() -> Unit) {
    with(pluginManager) {
        withPlugin("com.android.application") {
            configure<ApplicationExtension>(project = this@androidModule, block = block)
        }

        withPlugin("com.android.library") {
            configure<LibraryExtension>(project = this@androidModule, block = block)
        }
    }
}

internal inline fun <reified T: CommonExtension<*, *, *, *, *, *>> configure(project: Project, noinline block: T.() -> Unit) {
    with(project) {
        extensions.configure<T> {
            block.invoke(this)
        }
    }
}