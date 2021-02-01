package exporting

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pipeline.Coordinate
import pipeline.Exporter

class JsonExportTest {
    @Test
    fun `should export data to geoJson`() {
        //given
        val exporter: Exporter = JsonExporter()
        val coordinates: List<Coordinate> = listOf(
            Coordinate(8.489074, 53.615707),
            Coordinate(8.36147, 53.681942),
            Coordinate(8.214272, 53.749012)
        )
        //when
        val json = exporter.export(coordinates)
        //then
        Assertions.assertEquals(geoJson, json)
    }

    val geoJson = """
        {"type":"FeatureCollection","features":[{"type":"Feature","properties":{},"geometry":{"type":"Point","coordinates":[53.615707,8.489074]}},{"type":"Feature","properties":{},"geometry":{"type":"Point","coordinates":[53.681942,8.36147]}},{"type":"Feature","properties":{},"geometry":{"type":"Point","coordinates":[53.749012,8.214272]}}]}
    """.trimIndent()
}
