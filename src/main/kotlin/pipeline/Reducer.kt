package pipeline

interface Reducer {
    fun reduce(observations: List<Trip>) : List<Coordinate>
}
