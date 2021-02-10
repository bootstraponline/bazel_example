package cli
import java.io.BufferedInputStream
import java.io.InputStream
import java.util.*

// https://stackoverflow.com/questions/25878415/java-processbuilder-process-waiting-for-input
class StreamGobbler(inputStream: InputStream?) :
    Runnable {
    private val bufferedInput = BufferedInputStream(inputStream)
    private val scanner: Scanner = Scanner(bufferedInput)
    private val output = StringBuilder()
    override fun run() {
        while (scanner.hasNextLine()) {
            output.appendLine(scanner.nextLine())
        }
    }

    fun finish(): String {
        bufferedInput.close()
        scanner.close()

        return output.toString()
    }
}
