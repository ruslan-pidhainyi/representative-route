package pipeline

interface Exporter {
    fun export(coordinates: List<Coordinate>) : String
}
