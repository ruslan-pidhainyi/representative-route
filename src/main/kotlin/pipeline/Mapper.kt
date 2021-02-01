package pipeline

import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class Mapper {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    fun observationFrom(observationJson: String): Observation =
            json.decodeFromString(Observation.serializer(), observationJson)

    fun observationsFrom(observationJson: String): List<Observation> =
        json.decodeFromString(ListSerializer(Observation.serializer()), observationJson)
}
