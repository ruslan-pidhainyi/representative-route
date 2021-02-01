package pipeline

class Grouper {
}

const val numberOfGroups : Long = 300
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
