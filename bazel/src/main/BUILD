load("@rules_java//java:defs.bzl", "java_library", "java_binary")
load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_jvm_library")

#
# CLI app
#

kt_jvm_library(
    name = "app_lib",
    srcs = glob(["**/*.kt"]),
    deps = [
        "//:java_deps",
    ],
)

java_binary(
    name = "main",
    main_class = "cli.Main",
    runtime_deps = [":app_lib"],
)

