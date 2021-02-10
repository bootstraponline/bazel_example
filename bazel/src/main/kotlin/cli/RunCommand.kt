package cli

import java.util.concurrent.TimeUnit

object RunCommand {

    // https://stackoverflow.com/questions/25878415/java-processbuilder-process-waiting-for-input
// https://stackoverflow.com/a/52441962
    @JvmStatic
    fun run(cmd: List<String>, stdin: String = ""): String? = try {
        println(cmd.joinToString(" "))

        val process = ProcessBuilder(cmd)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectErrorStream(true)
            .start()

        val inputGobbler = StreamGobbler(process.inputStream)
        val inputGobblerThread = Thread(inputGobbler)
        inputGobblerThread.start()

        val processStdin = stdin.isNotBlank()

        if (processStdin) {
            process.outputStream.bufferedWriter().use { writer ->
                println("stdin: $stdin")
                writer.write(stdin)
                writer.flush()
            }
        }

        inputGobblerThread.join()

        if (!process.waitFor(2, TimeUnit.MINUTES)) {
            process.destroy()
            throw RuntimeException("Process timed out")
        }

        val outputText = inputGobbler.finish()
        inputGobblerThread.interrupt()

        if (processStdin) {
            process.destroy()
        } else {
            if (process.exitValue() != 0) {
                throw RuntimeException("exit code: ${process.exitValue()}\n$outputText")
            }
        }

        outputText
    } catch (e: java.io.IOException) {
        e.printStackTrace()
        null
    }

}
