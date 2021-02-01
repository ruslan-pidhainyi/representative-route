package pipeline

interface Reducer {
    fun reduce(observations: List<Observation>) : Observation
}
