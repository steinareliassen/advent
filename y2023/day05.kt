package y2023

import y2023.input.day5SoilRelations
import y2023.input.day5seebs

fun main() {

    fun mapToValue(relations: String, value: Long): Long {
        relations.split("\n").forEach {
            val (dest, source, range) = it.split(" ").map { it.trim().toLong() }
            if (value >= source && value <= source + range) {
                return dest + (value - source)
            }
        }
        return value
    }

    fun part1() {
        day5seebs.split(" ").map { it.trim().toLong() }.map { i ->
            day5SoilRelations.fold(Pair(i, i)) { acc, s ->
                Pair(acc.first, mapToValue(s, acc.second))
            }
        }.sortedBy { it.second }.first().let {
            println("Seeb ${it.first} planted in location ${it.second} <- THIS is the part 1 answer")
        }
    }

    fun part2() {
        // There is quite some refactor possibilities here, but I just wanted to get it done quickly :P
        val seeb = day5seebs.split(" ").chunked(2).map { (x, y) ->
            val start = x.toLong()
            val end = start + y.toLong()
            (start..end step 50000).map { i ->
                day5SoilRelations.fold(Pair(i, i)) { acc, s ->
                    Pair(acc.first, mapToValue(s, acc.second))
                }
            }.sortedBy { it.second }.first().let {
                println("Seeb ${it.first} planted in location ${it.second}")
                Pair(it.first, it.second)
            }
        }.sortedBy { it.second }.first().let {
            println("Seeb ${it.first} planted in location ${it.second}")
            it.first
        }

        println("hoaming in!")

        (seeb-50000..seeb+5000).map { i ->
            day5SoilRelations.fold(Pair(i, i)) { acc, s ->
                Pair(acc.first, mapToValue(s, acc.second))
            }
        }.sortedBy { it.second }.first().let {
            println("Seeb ${it.first} planted in location ${it.second} <- THIS value is the part 2 answer")
            Pair(it.first, it.second)
        }

    }

    part1()
    part2()
}
