package mapping

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Observation(
    @SerialName("from_port") val fromPort : String,
    @SerialName("to_port") val toPort : String,
    @SerialName("count") val numberOfPoints : Int,
    @SerialName("points") private val points : String
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
            longitude = it.longitude,
            latitude = it.latitude,
            knots = it.knots,
            millisecondsPassedFromStart = it.timestamp - startTime
        )
        }
        println(pointsWithTime)
        return pointsWithTime
    }

    private fun mapp(str: String) : Point {
        val arr = str.split(",")
        val knots = if (arr[3] == "null") 10.0
        else arr[3].toDouble()
        return Point(longitude = arr[0].toDouble(),
            latitude = arr[1].toDouble(),
            timestamp = arr[2].toLong(),
            knots = knots)
    }
}

@Serializable
data class Point(val longitude :Double, val latitude: Double, val timestamp: Long,  val knots : Double)

data class PointWithTime(val longitude :Double, val latitude: Double, val millisecondsPassedFromStart: Long,  val knots : Double)
