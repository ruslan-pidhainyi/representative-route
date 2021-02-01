package normalizing

import pipeline.Normalizer
import pipeline.Observation

class SimpleNormalizer(
    private val minNumberOfPoints: Long,
    private val maxDuration: Long,
    private val fromPort: String
    ) : Normalizer {
    override fun normalize(observations: List<Observation>): List<Observation> {
        return observations
            .filter { it.numberOfPoints >= minNumberOfPoints }
            .filter { it.duration < maxDuration }
            .filter { it.fromPort == fromPort }
    }
}
