package pipeline

import exporting.JsonExporter
import normalizing.FilteringNormalizer
import org.junit.jupiter.api.Test
import reducing.PositionByTimeBasedReducer
import uploading.JSONUploader

class SimplePipelineTest {

    @Test
    fun `should run pipeline`(){
        val uploader = JSONUploader()
        val normalizer = FilteringNormalizer(maxDuration = 39600000, minNumberOfPoints = 180)
        val reducer = PositionByTimeBasedReducer()
        val export = JsonExporter()
        val pipeline : Pipeline = SimplePipeline(uploader, normalizer, reducer, export)
        val path : String = this::class.java.classLoader.getResource("DEBRV_DEHAM_historical_routes.json").path
        val result = pipeline.runForData(path)
        println(result)
    }
}
