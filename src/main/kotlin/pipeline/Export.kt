package pipeline

interface Export {
    fun export(coordinates: List<Coordinate>) : String
}
