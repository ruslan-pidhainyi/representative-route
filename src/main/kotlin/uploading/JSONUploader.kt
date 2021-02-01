package uploading

import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import pipeline.Observation
import pipeline.Uploader
import java.io.File

class JSONUploader : Uploader {

    override fun uploadFromFile(fileName: String): List<Observation> {
        val json  = File(fileName).readText(Charsets.UTF_8)
        return observationsFrom(json)
    }

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    fun observationFrom(observationJson: String): Observation =
        json.decodeFromString(Observation.serializer(), observationJson)

    fun observationsFrom(observationJson: String): List<Observation> =
        json.decodeFromString(ListSerializer(Observation.serializer()), observationJson)
}
