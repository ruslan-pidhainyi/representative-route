package reducing

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import pipeline.Coordinate
import pipeline.Point
import pipeline.Trip

@Serializable
data class Observation(
    @SerialName("from_port") val fromPort : String,
    @SerialName("to_port") val toPort : String,
    @SerialName("count") val numberOfPoints : Int,
    @SerialName("points") private val points : String,
    @SerialName("leg_duration") val duration: Long
) {

    fun toTrip() : Trip =
        Trip(
            fromPort = fromPort,
            toPort = toPort,
            numberOfPoints = numberOfPoints,
            points = points(),
            duration = duration
        )

    private fun points(): List<Point>{
        val arrays = points.substring(1, points.length-2).trim()
        val g : List<String> = arrays
            .replace("[", "")
            .replace(" ", "")
            .split("],")
        return g.map {pointFrom(it)}
    }
    //this is mess, and should be fixed but i was not able to serialize json with ['a','b','c'] kind of array,
    // and discovered geojson when already wrote this
    private fun pointFrom(str: String) : Point {
        val arr = str.split(",")
        val knots = if (arr[3] == "null") 10.0
        else arr[3].toDouble()
        return Point(
            coordinate = Coordinate(longitude = arr[0].toDouble(), latitude = arr[1].toDouble()),
            timestamp = arr[2].toLong(),
            knots = knots)
    }
}




