package mapping

class Grouper {
}

const val numberOfGroups : Long = 196
val timeBoxed = (0..numberOfGroups).map { Pair(it*1000*60, it*1000*60*3) }

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

fun groupNumber(point: PointWithTime) : Int =
    timeBoxed
        .indexOfFirst { it.first <= point.millisecondsPassedFromStart && it.second > point.millisecondsPassedFromStart }
