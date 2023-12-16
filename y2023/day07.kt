package y2023

import y2023.input.day7PokerPlay

fun main() {

    val day7PokerMatrix = day7PokerPlay.split("\n")

    fun toCardInt(card: Char) =
        when (card) {
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'J' -> 11
            'T' -> 10
            else -> card.digitToInt()
        }

    fun score(card: String) = card.toList()
        .groupBy { it }
        .map { it.value.size }.sortedDescending().let {
            when {
                it[0] == 5 -> 7
                it[0] == 4 -> 6
                it[0] == 3 && it[1] == 2 -> 5
                it[0] == 3 -> 4
                it[0] == 2 && it[1] == 2 -> 3
                it[0] == 2 -> 2
                else -> 1
            }
        }

    fun compare(card: String, ocard: String): Boolean =
        Pair(toCardInt(card.first()), toCardInt(ocard.first())).let {
            if (it.first == it.second) compare(card.substring(1), ocard.substring(1))
            else it.first > it.second
        }

    fun maximize(cards: String) =
        cards.replace('J', cards.toList().reduce { x, y ->
            if (y == 'J') x else
                if (score(cards.replace('J', x)) > score(cards.replace('J', y)))
                    x else y
        })

    fun part1() =
        day7PokerMatrix.mapIndexed { index, line ->
            val (cards, bet) = line.split(" ")
            (day7PokerMatrix.mapIndexed { oindex, oline ->
                val (ocards, _) = oline.split(" ")
                if (index == oindex) 0
                else if (score(cards) == score(ocards)) {
                    if (compare(cards, ocards)) 1 else 0
                } else
                    if (score(cards) > score(ocards)) 1 else 0
            }.sum() + 1) * bet.toInt()
        }.sum()

    fun part2() =
        day7PokerMatrix.mapIndexed { index, line ->
            val (cardslow, bet) = line.split(" ")
            (day7PokerMatrix.mapIndexed { oindex, oline ->
                val (ocardslow, _) = oline.split(" ")
                val ocards = maximize(ocardslow)
                val cards = maximize(cardslow)
                if (index == oindex) 0
                else if (score(cards) == score(ocards)) {
                    if (compare(cardslow, ocardslow)) 1 else 0
                } else
                    if (score(cards) > score(ocards)) 1 else 0
            }.sum() + 1) * bet.toInt()
        }.sum()

    println(part1())
    println(part2())
}