package j2v8

import com.eclipsesource.v8.V8

/**
 * Created by sweety on 2016/9/1.
 */

fun main(args: Array<String>) {
    val runtime = V8.createV8Runtime()
    val result = runtime.executeStringScript("var a = new Date(); a.toString()")
    print(result)
    runtime.release()
}
