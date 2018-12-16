import java.io.File

val Day04 = fun() {

    val pattern = Regex("""\[.*(\d\d)] (falls asleep|wakes up|Guard #(\d+) begins shift)""")
    var currentGuardId = -1
    val events = inputs04.useLines {
        it.sorted()
        .map { line ->

            val (minutes, event, id) = pattern.matchEntire(line)!!.groupValues.drop(1)

            if (id.isNotEmpty()) {
                currentGuardId = id.toInt()
            }

            val eventType = when (event.first()) {
                'G' -> EventType.SHIFT_BEGINS
                'f' -> EventType.FALLS_ASLEEP
                else -> {
                    EventType.WAKES_UP
                }
            }

            Event(eventType, minutes.toInt(), currentGuardId)
        }
    }

    val sleepMinutes = sequence {
        var currentGuardID = -1
        var asleepSince = -1
        for (event in events) {
            when (event.eventType) {
                EventType.SHIFT_BEGINS -> currentGuardID = event.guardId
                EventType.FALLS_ASLEEP -> asleepSince = event.minutes
                EventType.WAKES_UP -> yieldAll((asleepSince until event.minutes).map {  Pair(currentGuardID, it) })
            }
        }
    }

    val guardMostSleeping = sleepMinutes
        .groupingBy { it.first }
        .eachCount()
        .maxBy { it.value }
        ?.key

    val minMostAsleep = sleepMinutes
        .groupBy { it.first }[guardMostSleeping]
        ?.groupingBy { it.second }
        ?.eachCount()
        ?.maxBy { it.value }
        ?.key

    println("most sleeping is guard ID $guardMostSleeping with $minMostAsleep most sleeped minute, result 4a: ${guardMostSleeping!! * minMostAsleep!!}")

    val (guard, minutes) = sleepMinutes
        .groupingBy { it }
        .eachCount().print()
        .maxBy { it.value }!!
        .key

    println("most sleeping is guard ID $guard with $minutes most sleeped minute, result 4b: ${guard * minutes}")
}


data class Event(val eventType: EventType, val minutes: Int, val guardId: Int)

enum class EventType {
    SHIFT_BEGINS,
    FALLS_ASLEEP,
    WAKES_UP
}

val inputs04 = File("inputs/4.txt")

const val testData04 =
"""[1518-11-01 00:00] Guard #10 begins shift
[1518-11-01 00:05] falls asleep
[1518-11-01 00:25] wakes up
[1518-11-01 00:30] falls asleep
[1518-11-01 00:55] wakes up
[1518-11-01 23:58] Guard #99 begins shift
[1518-11-02 00:40] falls asleep
[1518-11-02 00:50] wakes up
[1518-11-03 00:24] falls asleep
[1518-11-03 00:29] wakes up
[1518-11-04 00:02] Guard #99 begins shift
[1518-11-04 00:36] falls asleep
[1518-11-04 00:46] wakes up
[1518-11-05 00:03] Guard #99 begins shift
[1518-11-05 00:45] falls asleep
[1518-11-05 00:55] wakes up"""
