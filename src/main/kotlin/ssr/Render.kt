package ssr

import java.io.File
import java.util.concurrent.Executors
import javax.annotation.Resource
import javax.script.ScriptContext
import javax.script.ScriptEngineManager

/**
 * Created by sweety on 2017/4/13.
 */

class Render {
    companion object {
        @JvmStatic
        fun render() {
            val globalScheduledThreadPool = Executors.newScheduledThreadPool(20)
            val engine = ScriptEngineManager().getEngineByName("nashorn")
            engine.context.setAttribute("__NASHORN_POLYFILL_TIMER__", globalScheduledThreadPool, ScriptContext.ENGINE_SCOPE)
//    val compilingEngine = engine as Compilable
            val bundleCode = ResourceReader.readText("server.react.bundle.js")
            val reactCode = ResourceReader.readText("react.js")
            val reactDomCode = ResourceReader.readText("react-dom-server.js")
            val polyfillCode = ResourceReader.readText("nashorn-polyfill.js")
            engine.eval("$polyfillCode;var bundle = (function() { var module = {}; $bundleCode; return module.exports;})(); $reactCode; $reactDomCode;" +
                    "console.log(ReactDOMServer.renderToString(React.createElement(bundle.default, {})));" +
                    "global.nashornEventLoop.process();")
//    val compiledBundle = compilingEngine.compile("var module = {};" + bundleCode)
//    val bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE)
//    for (me in bindings.entries) {
//        System.out.printf("%s: %s\n", me.key, me.value.toString())
//    }
//    compiledBundle.eval(bindings)
        }
    }
}