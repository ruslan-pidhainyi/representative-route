package reducing

import pipeline.Coordinate
import pipeline.Reducer
import pipeline.Trip
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class PointWithTime(val coordinate: Coordinate, val millisecondsPassedFromStart: Long)

class PositionByTimeBasedReducer(private val timeBoxing: TimeBoxing = TimeBoxing(400,1)) : Reducer {

    override fun reduce(observations: List<Trip>): List<Coordinate> {
        val groupedCoordinates = groupByPositionDuringTimeSlot(observations)
        return averageCoordinatesInGroups(groupedCoordinates)
    }

    private fun groupByPositionDuringTimeSlot(trips: List<Trip>) : Map<Int, List<PointWithTime>> {
        val fromPort  = trips[0].fromPort
        return trips
            .filter { it.fromPort != fromPort }
            .map { pointsWithTimePassed(it, fromPort) }
            .map { groupByMillisecondsPassed(it) }
            .reduce(::mergeMaps)
    }

    private fun pointsWithTimePassed(trip: Trip, fromPort : String) : List<PointWithTime> {
        return if(trip.toPort == fromPort) {
           pointsWithTimePassedOneWay(trip)
        }else {
           pointsWithTimePassedWayBack(trip)
        }
    }

    private fun pointsWithTimePassedOneWay(trip: Trip) : List<PointWithTime> {
        val sortedPoints = trip.points.sortedBy { it.timestamp }
        val startTime = sortedPoints[0].timestamp
        return sortedPoints.map { PointWithTime(
                coordinate = it.coordinate,
                millisecondsPassedFromStart = it.timestamp - startTime
            )}
    }

    private fun pointsWithTimePassedWayBack(trip: Trip) : List<PointWithTime> {
        val sortedPoints = trip.points.sortedByDescending { it.timestamp }
        val endTime = sortedPoints[0].timestamp
        return sortedPoints.map { PointWithTime(
            coordinate = it.coordinate,
            millisecondsPassedFromStart = endTime - it.timestamp
        )}
    }


    private fun groupByMillisecondsPassed(points : List<PointWithTime>): Map<Int, List<PointWithTime>> {
        return points.groupBy { timeBoxing.timeBox(it) }
    }

    private fun mergeMaps(first: Map<Int, List<PointWithTime>>, second: Map<Int, List<PointWithTime>>)
        : Map<Int, List<PointWithTime>>  =
        (first.asSequence() + second.asSequence())
            .groupBy({ it.key }, { it.value })
            .mapValues { it.value.flatten() }

    private fun averageCoordinatesInGroups(grouped : Map<Int, List<PointWithTime>>) : List<Coordinate> {
        return grouped
            .mapValues { group -> group.value.map { it.coordinate } }
            .map {center(it.value)}
    }

}

class TimeBoxing(numberOfSlots : Long, interval : Long){
    private val timeBoxed = (0..numberOfSlots step interval ).map { Pair(it*1000*60, it*1000*60*2) }

    fun timeBox(point: PointWithTime) : Int =
        timeBoxed
            .indexOfFirst { point.millisecondsPassedFromStart >= it.first &&  point.millisecondsPassedFromStart < it.second }

}

//https://gis.stackexchange.com/questions/7555/computing-an-averaged-latitude-and-longitude-coordinates/7566
//https://stackoverflow.com/questions/6671183/calculate-the-center-point-of-multiple-latitude-longitude-coordinate-pairs

private const val pi = Math.PI / 180
private const val xpi = 180 / Math.PI

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


