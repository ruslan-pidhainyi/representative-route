package normalizing

import pipeline.Normalizer
import pipeline.Trip

class SimpleNormalizer(
    private val minNumberOfPoints: Long,
    private val maxDuration: Long,
    private val fromPort: String
    ) : Normalizer {
    override fun normalize(observations: List<Trip>): List<Trip> {
        return observations
            .filter { it.numberOfPoints >= minNumberOfPoints }
            .filter { it.duration < maxDuration }
            .filter { it.fromPort == fromPort }
    }
}
