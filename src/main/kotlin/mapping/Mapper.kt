package mapping

import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import java.time.Instant

class Mapper {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    fun observationFrom(observationJson: String): Observation =
            json.decodeFromString(Observation.serializer(), observationJson)
}
