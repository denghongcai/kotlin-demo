package ssr

import org.openjdk.jmh.annotations.*

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
open class RenderBenchmark {
    var render : Render? = null
    @Setup
    fun setUp(): Unit {
        render = Render()
    }

    @Benchmark
    fun testRender() {
        render!!.render()
    }

}