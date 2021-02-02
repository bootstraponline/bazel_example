#
# Kotlin Toolchain
#
load("@rules_java//java:defs.bzl", "java_library")
load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "define_kt_toolchain")
package(default_visibility = ["//visibility:public"])

KOTLIN_LANGUAGE_LEVEL = "1.4"
JAVA_LANGUAGE_LEVEL = "1.8"

define_kt_toolchain(
    name = "kotlin_toolchain",
    api_version = KOTLIN_LANGUAGE_LEVEL,
    jvm_target = JAVA_LANGUAGE_LEVEL,
    language_version = KOTLIN_LANGUAGE_LEVEL,
)

#
# Deps
#

java_library(
    name = "java_deps",
    exports = [
        "@maven//:com_github_ajalt_clikt_clikt",
    ],
)
