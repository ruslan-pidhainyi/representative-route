package pipeline

interface Normalizer {
    fun normalize(observations : List<Observation>) : List<Observation>
}
