## gradle setup

Build

`./gradlew assemble`

Build release jar

`./gradlew shadowJar`

Run release jar

`java -jar ./build/libs/coverage.jar`

## graal vm native image

`native-image -jar coverage.jar`

## bazel setup

Build all
- `bazel build //...`

---

`brew install bazel buildifier`

https://github.com/bazelbuild/rules_kotlin/blob/master/examples/trivial/BUILD

`com.expedia:graphql-kotlin:0.6.1` becomes
`com_expedia_graphql_kotlin`

List all targets
- `bazel query ...`

//app:myapp
//app:app_lib
//:java_deps

kt_jvm_library(name = "app_lib",

java_binary(name = "myapp",
    main_class = "app.MyApp",

java_library(name = "java_deps",

~~~

//src/main/kotlin:main
//src/main/kotlin:app_lib
//src/main:java_deps

bazel build //src/main/kotlin:main


Run output:
- `./bazel-bin/src/main`




