package cli

import com.uber.jenkins.phabricator.conduit.ConduitAPIClient
import com.uber.jenkins.phabricator.conduit.DifferentialClient
import com.uber.jenkins.phabricator.conduit.HarbormasterClient
import com.uber.jenkins.phabricator.lint.LintResults
import com.uber.jenkins.phabricator.tasks.SendHarbormasterResultTask
import com.uber.jenkins.phabricator.tasks.Task
import com.uber.jenkins.phabricator.unit.UnitResults
import com.uber.jenkins.phabricator.utils.Logger
import java.lang.reflect.Field
import java.lang.reflect.Method

class Main {
    companion object {
        fun postCoverage(
            diffID: String,
            conduitURL: String,
            conduitToken: String,
            phid: String,
            harbormasterCoverage: Map<String, String>
        ) {
            println("diffID: $diffID, conduitURL: $conduitURL, phid: $phid")

            val conduitAPIClient = ConduitAPIClient(conduitURL, conduitToken)

            val logger = Logger(System.out)
            val diffClient = DifferentialClient(diffID, conduitAPIClient)
            val messageType: HarbormasterClient.MessageType = HarbormasterClient.MessageType.work
            val unitResults: UnitResults? = null
            val lintResults: LintResults? = null

            val result = SendHarbormasterResultTask(
                logger,
                diffClient,
                phid,
                messageType,
                unitResults,
                harbormasterCoverage,
                lintResults
            ).run()

            if (result != Task.Result.SUCCESS) {
                println("Coverage posting failed!")
            }
        }

        private fun getenv(name: String): String {
            return System.getenv(name) ?: throw Exception("$name environment variable not found")
        }

        // Groovy has reflective access warnings.
        // https://stackoverflow.com/a/53517025
        fun disableAccessWarnings() {
            try {
                val unsafeClass = Class.forName("sun.misc.Unsafe")
                val field: Field = unsafeClass.getDeclaredField("theUnsafe")
                field.isAccessible = true
                val unsafe: Any = field.get(null)
                val putObjectVolatile: Method = unsafeClass.getDeclaredMethod(
                    "putObjectVolatile",
                    Any::class.java,
                    Long::class.javaPrimitiveType,
                    Any::class.java
                )
                val staticFieldOffset: Method = unsafeClass.getDeclaredMethod("staticFieldOffset", Field::class.java)
                val loggerClass = Class.forName("jdk.internal.module.IllegalAccessLogger")
                val loggerField: Field = loggerClass.getDeclaredField("logger")
                val offset = staticFieldOffset.invoke(unsafe, loggerField) as Long
                putObjectVolatile.invoke(unsafe, loggerClass, offset, null)
            } catch (ignored: java.lang.Exception) {
            }
        }

        @JvmStatic
        fun main(args: Array<String>) {
            disableAccessWarnings()

            val diffID = getenv("PHABRICATOR_DIFF_ID")
            val conduitURL = getenv("PHABRICATOR_URL")
            val conduitToken = getenv("PHABRICATOR_API_TOKEN")
            val phid = getenv("HARBORMASTER_BUILD_TARGET_PHID")
            val harbormasterCoverage = mutableMapOf(
                "test_diff.go" to "UCCU",
            )

            postCoverage(
                diffID = diffID,
                conduitURL = conduitURL,
                conduitToken = conduitToken,
                phid = phid,
                harbormasterCoverage = harbormasterCoverage
            )


            // 1. post fake coverage data to an open diff
            // 2. convert golang coverage and post to open diff
            // 3. add examples for other languages
            // 4. refactor/cleanup code.
            // 5. update bazel scripts (move to bazel-deps?)
        }
    }
}
