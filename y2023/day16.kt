package y2023

import splitLine
import y2023.input.day16Input
import kotlin.math.max

enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

fun main() {
    val matrix = day16Input.splitLine().map { it.toList() }

    fun nextPos(x: Int, y: Int, direction: Direction) =
        when (direction) {
            Direction.WEST -> Triple(x - 1, y, direction)
            Direction.EAST -> Triple(x + 1, y, direction)
            Direction.NORTH -> Triple(x, y - 1, direction)
            Direction.SOUTH -> Triple(x, y + 1, direction)
        }

    fun traverseFrom(x: Int, y: Int, direction: Direction): Int {
        val visitedMap = mutableSetOf<Triple<Int, Int, Direction>>()

        fun traverse(x: Int, y: Int, direction: Direction) {
            // Beam out of bounds?
            if (x < 0 || x > matrix[0].size - 1) return
            if (y < 0 || y > matrix.size - 1) return

            // If we have already been here in the exact same direction, exit
            if (visitedMap.contains(Triple(x, y, direction)))
                return


            // Mark as visited
            visitedMap.add(Triple(x, y, direction))

            // Find next position. Do a split if needed.
            val (nextX, nextY, nextDirection) = when (matrix[y][x]) {
                '.' -> nextPos(x, y, direction)
                '/' -> nextPos(
                    x,
                    y,
                    when (direction) {
                        Direction.SOUTH -> Direction.WEST
                        Direction.EAST -> Direction.NORTH
                        Direction.NORTH -> Direction.EAST
                        Direction.WEST -> Direction.SOUTH
                    }
                )

                '\\' -> nextPos(
                    x,
                    y,
                    when (direction) {
                        Direction.SOUTH -> Direction.EAST
                        Direction.EAST -> Direction.SOUTH
                        Direction.NORTH -> Direction.WEST
                        Direction.WEST -> Direction.NORTH
                    }
                )

                '-' ->
                    if (direction == Direction.WEST || direction == Direction.EAST) nextPos(x, y, direction)
                    else {
                        val (splitX, splitY, splitDirection) = nextPos(x, y, Direction.WEST)
                        traverse(splitX, splitY, splitDirection)
                        nextPos(x, y, Direction.EAST)
                    }

                '|' ->
                    if (direction == Direction.SOUTH || direction == Direction.NORTH) nextPos(x, y, direction)
                    else {
                        val (splitX, splitY, splitDirection) = nextPos(x, y, Direction.NORTH)
                        traverse(splitX, splitY, splitDirection)
                        nextPos(x, y, Direction.SOUTH)
                    }

                else -> throw Error("Should not happen")
            }

            traverse(nextX, nextY, nextDirection)
        }

        // Start beaming the map
        traverse(x, y, direction)

        // Extract visited locations
        val visitedLocations = visitedMap.map { (x, y) -> Pair(x, y) }

        // Create visited matrix and calculate sum of visited elements
        return matrix.mapIndexed { my, row ->
            List(row.size) { mx ->
                if (visitedLocations.contains(Pair(mx, my))) '#' else '.'
            }
        }.sumOf { row ->
            row.filter { it == '#' }.size
        }
    }

    fun part1() =
        traverseFrom(0, 0, Direction.EAST)

    fun part2() =
        matrix.indices.maxOfOrNull {
            max(
                max(
                    traverseFrom(0, it, Direction.EAST),
                    traverseFrom(day16Input.length - 1, it, Direction.WEST)
                ),
                max(
                    traverseFrom(it, 0, Direction.SOUTH),
                    traverseFrom(it, matrix[0].size - 1, Direction.NORTH)
                )
            )
        }

    println("Part 1 answer : ${part1()}")
    println("Part 2 answer : ${part2()}")

}