package exporting

import org.junit.jupiter.api.Test
import pipeline.Coordinate
import pipeline.Exporter

class JsonExportTest {
    @Test
    fun `should export data`(){
        //given
        val exporter : Exporter = JsonExporter()
        val coordinates : List<Coordinate> = listOf(
            Coordinate(8.489074, 53.615707),
            Coordinate(8.36147, 53.681942),
            Coordinate(8.214272, 53.749012)
        )
        //when
        val json = exporter.export(coordinates)
        //the
        println(json)
    }
}
