package normalizing

import pipeline.Normalizer
import pipeline.Trip

class FilteringNormalizer(
    private val minNumberOfPoints: Long,
    private val maxDuration: Long,
    ) : Normalizer {
    override fun normalize(observations: List<Trip>): List<Trip> {
        return observations
            .filter { it.numberOfPoints >= minNumberOfPoints }
            .filter { it.duration < maxDuration }
    }
}
