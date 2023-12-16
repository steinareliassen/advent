package y2023

import y2023.input.cards

fun main() {
    fun part1() =
        cards.split("\n").map { line ->
            val (_, cards) = line.split(":")
            val (ticket, winner) = cards.split("|")
            val ticketNums = ticket.split(" ").mapNotNull {
                try {
                    it.toInt()
                } catch (e: Exception) {
                    null
                }
            }
            val winnerNums = winner.split(" ").mapNotNull {
                try {
                    it.toInt()
                } catch (e: Exception) {
                    null
                }
            }
            Math.pow(2.0, (ticketNums.intersect(winnerNums).size - 1).toDouble()).toInt()
        }.sum()

    fun part2(): Int {
        fun strToIntArr(numStr : String) =
            numStr.split(" ").mapNotNull { try {it.toInt()} catch (e : Exception) {null} }

        val cardMap = cards.split("\n").associate { line ->
            val (idString, cards) = line.split(":")
            val id = idString.substring(4).trim().toInt()
            val (ticket, winner) = cards.split("|")
            id to strToIntArr(ticket).intersect(
                strToIntArr(winner)
            ).size
        }

        fun traverse(start : Int, count : Int) : Int =
            (start+count-1).let { end ->
                if (start > cardMap.size) 0
                else (start..if (end>=cardMap.size) cardMap.size else end).map {
                    if (cardMap[it] == 0) 1
                    else 1 + traverse(it + 1, cardMap[it]!!)
                }.sum()
            }

        return traverse(1,cardMap.size)
    }

    println(part1())
    println(part2())

}

