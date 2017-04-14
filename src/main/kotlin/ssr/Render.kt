package ssr

import java.io.File
import java.util.concurrent.Executors
import javax.annotation.Resource
import javax.script.*
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

/**
 * Created by sweety on 2017/4/13.
 */

class Render{
    val engine : ScriptEngine
    val compiled : CompiledScript

    fun render() {
        compiled.eval()
//    val compiledBundle = compilingEngine.compile("var module = {};" + bundleCode)
//    val bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE)
//    for (me in bindings.entries) {
//        System.out.printf("%s: %s\n", me.key, me.value.toString())
//    }
//    compiledBundle.eval(bindings)
    }

    init {
        val globalScheduledThreadPool = Executors.newScheduledThreadPool(20)
        engine = ScriptEngineManager().getEngineByName("nashorn")
        engine.context.setAttribute("__NASHORN_POLYFILL_TIMER__", globalScheduledThreadPool, ScriptContext.ENGINE_SCOPE)
        val bundleCode = ResourceReader.readText("server.react.bundle.js")
        val reactCode = ResourceReader.readText("react.js")
        val reactDomCode = ResourceReader.readText("react-dom-server.js")
        val polyfillCode = ResourceReader.readText("nashorn-polyfill.js")
        engine.eval("$polyfillCode;var bundle = (function() { var module = {}; $bundleCode; return module.exports;})(); $reactCode; $reactDomCode;")
        val mockCode = ResourceReader.readText("mockData.js")
        engine.eval(mockCode)
        compiled = (engine as Compilable).compile("ReactDOMServer.renderToString(React.createElement(bundle.default, {listData: listData, bannerData: bannerData}));" +
                "global.nashornEventLoop.process();")
    }
}