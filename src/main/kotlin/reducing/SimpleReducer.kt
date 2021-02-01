package reducing

import pipeline.Coordinate
import pipeline.Observation
import pipeline.Reducer
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class PointWithTime(val coordinate: Coordinate, val millisecondsPassedFromStart: Long,  val knots : Double)

class SimpleReducer : Reducer {
    override fun reduce(observations: List<Observation>): List<Coordinate> {
        val f = map(observations)
        return mapToCenter(f)
    }

    val numberOfGroups : Long = 300
    val timeBoxed = (0..numberOfGroups).map { Pair(it*1000*60, it*1000*60*2) }

    fun groupByMillisecondsFromStart(points : List<PointWithTime>): Map<Int, List<PointWithTime>> {
        //in average position is red every 3 minutes -> create map with time boxes of 3 min
        return points.groupBy { groupNumber(it) }
    }

    fun mergeMaps(first: Map<Int, List<PointWithTime>>, second: Map<Int, List<PointWithTime>>) : Map<Int, List<PointWithTime>> {
        val map = (first.asSequence() + second.asSequence())
            .groupBy({ it.key }, { it.value })
            .mapValues { it.value.flatten() }
        return map
    }

    fun map(observations: List<Observation>) : Map<Int, List<PointWithTime>> {
        return observations
            .map { it.pointsWithTimePassedFromStart() }
            .map { groupByMillisecondsFromStart(it) }
            .reduce(::mergeMaps)
    }

    fun mapToCenter(grouped : Map<Int, List<PointWithTime>>) : List<Coordinate> {
        val v : Map<Int, List<Coordinate>> = grouped.mapValues { it.value.map { it.coordinate } }
        return v.map { center(it.value) }
    }



    fun groupNumber(point: PointWithTime) : Int =
        timeBoxed
            .indexOfFirst { it.first <= point.millisecondsPassedFromStart && it.second > point.millisecondsPassedFromStart }

}

private const val pi = Math.PI / 180
private const val xpi = 180 / Math.PI
//https://stackoverflow.com/questions/6671183/calculate-the-center-point-of-multiple-latitude-longitude-coordinate-pairs
fun center(coordinates: List<Coordinate>): Coordinate {
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
