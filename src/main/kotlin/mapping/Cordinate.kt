package mapping

import kotlinx.serialization.Serializable
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

private const val pi = Math.PI / 180
private const val xpi = 180 / Math.PI

fun center(coordinates : List<Coordinate>): Coordinate {
    if (coordinates.size == 1) {
        return coordinates[0]
    }
    var x = 0.0
    var y = 0.0
    var z = 0.0
    for (c in coordinates) {
        val latitude: Double = c.latitude * pi
        val longitude: Double = c.longitude * pi
        val cl = cos(latitude) //save it as we need it twice
        x += cl * cos(longitude)
        y += cl * sin(longitude)
        z += sin(latitude)
    }
    val total = coordinates.size
    x /= total
    y /= total
    z /= total
    val centralLongitude = atan2(y, x)
    val centralSquareRoot = sqrt(x * x + y * y)
    val centralLatitude = atan2(z, centralSquareRoot)
    return Coordinate(centralLatitude * xpi, centralLongitude * xpi)
}
@Serializable
data class Coordinate(val latitude : Double, val longitude : Double)
