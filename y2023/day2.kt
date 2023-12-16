package y2023

import y2023.input.day2games

fun main() {

    fun part1(): Int {
        val colourMax = listOf("red" to 12, "green" to 13, "blue" to 14)

        fun isOk(okString: String): Boolean {
            okString.split(";").forEach { colours ->
                colours.split(",").forEach { colour ->
                    colourMax.forEach {
                        if (colour.endsWith(it.first)) {
                            if (colour.replace(" ${it.first}", "").trim().toInt() > it.second)
                                return false
                        }
                    }
                }
            }
            return true
        }

        return day2games.split("\n").sumOf { game ->
            val (a, b) = game.split(":")
            if (isOk(b)) a.substring(5).toInt() else 0
        }

    }

    fun part2(): Int {
        val colourList = listOf("red", "green", "blue")

        fun isOk(okString: String): Int {
            val bucket = mutableMapOf<String, Int>()
            okString.split(";").forEach { colours ->
                colours.split(",").forEach { colour ->
                    colourList.forEach {
                        if (colour.endsWith(it)) {
                            val value = colour.replace(" $it", "").trim().toInt()
                            if (!bucket.containsKey(it) || bucket[it]!! < value) bucket[it] = value
                        }
                    }
                }
            }
            return bucket.values.reduce { acc, i -> acc * i }
        }

        return day2games.split("\n").map { game ->
            val (_, b) = game.split(":")
            isOk(b)
        }.sum()

    }

    println(part1())
    println(part2())
}

