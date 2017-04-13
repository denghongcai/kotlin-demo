package ssr

import java.io.BufferedReader
import java.io.InputStreamReader
import sun.text.normalizer.UTF16.append
import jdk.nashorn.internal.runtime.ScriptingFunctions.readLine



/**
 * Created by sweety on 2017/4/13.
 */

class ResourceReader {
    companion object {
        @JvmStatic
        fun readText(path : String) : String {
            val stream = Thread.currentThread().contextClassLoader.getResourceAsStream(path)
            val streamReader = InputStreamReader(stream, "UTF8")
            return BufferedReader(streamReader).readText()
        }
    }
}