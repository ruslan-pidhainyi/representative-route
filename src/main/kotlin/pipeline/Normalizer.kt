package pipeline

interface Normalizer {
    fun normalize(observations : List<Trip>) : List<Trip>
}
