package pipeline

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import reducing.PointWithTime

@Serializable
data class Observation(
    @SerialName("from_port") val fromPort : String,
    @SerialName("to_port") val toPort : String,
    @SerialName("count") val numberOfPoints : Int,
    @SerialName("points") private val points : String,
    @SerialName("leg_duration") val duration: Long
) {


    fun points(): List<Point>{
        val arrays = points.substring(1, points.length-2).trim()
        val g : List<String> = arrays
            .replace("[", "")
            .replace(" ", "")
            .split("],")
        return g.map {mapp(it)}
    }

    fun pointsWithTimePassedFromStart() : List<PointWithTime> {
        val sortedPoints = points().sortedBy { it.timestamp }
        val startTime = sortedPoints[0].timestamp
        val pointsWithTime = sortedPoints.map { PointWithTime(
            coordinate = it.coordinate,
            knots = it.knots,
            millisecondsPassedFromStart = it.timestamp - startTime
        )
        }
        return pointsWithTime
    }

    private fun mapp(str: String) : Point {
        val arr = str.split(",")
        val knots = if (arr[3] == "null") 10.0
        else arr[3].toDouble()
        return Point(
            coordinate = Coordinate(longitude = arr[0].toDouble(), latitude = arr[1].toDouble()),
            timestamp = arr[2].toLong(),
            knots = knots)
    }
}

@Serializable
data class Point(val coordinate: Coordinate, val timestamp: Long,  val knots : Double)

