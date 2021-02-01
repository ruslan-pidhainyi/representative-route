package pipeline


data class Trip(
    val fromPort : String,
    val toPort : String,
    val numberOfPoints: Int,
    val points: List<Point>,
    val duration: Long
)

data class Point(val coordinate: Coordinate, val timestamp: Long,  val knots : Double)

data class Coordinate(val latitude: Double, val longitude: Double)
