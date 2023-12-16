package y2023

import y2023.input.day8direction
import y2023.input.day8gps

fun main() {
    var currentDirection = 0

    fun nextDirection() =
        if (currentDirection >= day8direction.size) {
            currentDirection = 0
            day8direction[currentDirection++]
        } else day8direction[currentDirection++]

    fun getMap() = day8gps.split("\n").associate { line ->
        val (a, b) = line.split("=")

        val (c,d) = b.trim().substring(1,b.length-2).split(",")
        a.trim() to Pair(c.trim(), d.trim())
    }

    fun part1() {
        val map = getMap()
        var count = 0
        var location : String? = "AAA"
        do {
            count++
            location = if (nextDirection() == 'L') map[location]?.first else map[location]?.second
        } while (location != "ZZZ")

        println(count)
    }

    fun part2() {
        currentDirection = 0
        val map = getMap()
        var count = 0L
        val locations = map.mapNotNull { if( it.key.endsWith("A") ) it.key else null  }.toTypedArray()
        val cycleCount = Array(locations.size) { 0L }
        do {
            count++
            val direction = nextDirection()
            (0..locations.size-1).forEach {
                locations[it] = if (direction == 'L') map[locations[it]]!!.first else map[locations[it]]!!.second
                if (locations[it].endsWith("Z") && cycleCount[it]==0L) cycleCount[it] = count
            }

        } while (cycleCount.any { it == 0L })
        // OK, I don't want to make this tool so I cheated here:
        println("Output of length of each cycle. Use a Least common multiple tool with these as input to find answer:")
        cycleCount.forEach {
            println(it)
        }
    }

    part1()
    part2()

}

