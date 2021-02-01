package uploading

import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import pipeline.Trip
import pipeline.Uploader
import reducing.Observation
import java.io.File

class JSONUploader : Uploader {

    override fun uploadFromFile(fileName: String): List<Trip> {
        val json  = File(fileName).readText(Charsets.UTF_8)
        return observationsFrom(json)
    }

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    fun observationsFrom(observationJson: String): List<Trip> =
        json.decodeFromString(ListSerializer(Observation.serializer()), observationJson).map { it.toTrip() }
}
