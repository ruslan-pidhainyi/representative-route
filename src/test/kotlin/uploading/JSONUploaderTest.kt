package uploading

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pipeline.Observation
import pipeline.Uploader

class JSONUploaderTest {
    @Test
    fun `should load observation data from JSON file`() {
        //given
        val uploader : Uploader = JSONUploader()
        val path : String = this::class.java.classLoader.getResource("Uploader-test-data.json").path
        //when
        val observations : List<Observation> = uploader.uploadFromFile(path)
        //then
        Assertions.assertEquals(4, observations.size)
    }
}
