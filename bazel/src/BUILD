load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "app_lib",
    srcs = glob(["**/*.kt"]),
)

java_binary(
    name = "main",
    main_class = "cli.Main",
    runtime_deps = [":app_lib"],
)
