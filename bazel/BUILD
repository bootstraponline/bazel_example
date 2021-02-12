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
        "@maven//:org_apache_httpcomponents_httpcomponents_client_4_5_13",
        "@maven//:commons_httpclient_commons_httpclient_3_1",
        "@maven//:org_apache_httpcomponents_httpcore_4_4_6",
        "@maven//:org_apache_httpcomponents_httpclient_4_5_3",
        "@maven//:commons_io_commons_io_2_4",
        "@maven//:commons_lang_commons_lang_2_6",
        "@maven//:org_codehaus_groovy_groovy_all_2_4_6",
        "@maven//:net_sf_ezmorph_ezmorph_1_0_6",
        "@maven//:org_kohsuke_stapler_json_lib_2_4_jenkins_3",
    ],
)
