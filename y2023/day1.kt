package y2023

import y2023.input.day1input

fun main() {

    val day1array = day1input.split("\n")
    // Day 1 Part 1 
    println(day1array.map { line ->
        "${line.find { it.isDigit() }}${line.reversed().find { it.isDigit() }}".toInt()
    }.sum())

    // Day 1 Part 2 
    fun numberify(input : String, startEnd : Boolean = false) : String =
        numbers.reduce {
                x,y ->
            val xpos = if (startEnd) input.lastIndexOf(x.second) else input.indexOf(x.second)
            val ypos = if (startEnd) input.lastIndexOf(y.second) else input.indexOf(y.second)

            if (xpos == -1) y else
                if (ypos == -1) x else
                    if (startEnd) {if (xpos > ypos) x else y}
                    else {if (xpos > ypos) y else x}
        }.let {
            if (it.first == 0) input
            else if (startEnd)
                numberify(input.replaceLast(it.second, it.first.toString()))
            else
                numberify(input.replaceFirst(it.second, it.first.toString()))
        }

    println(day1array.map { txtline ->
        (numberify(txtline).let { line -> "${line.find { it.isDigit() }}" } +
            numberify(txtline,true).let { line -> "${line.reversed().find { it.isDigit() }}" }).toInt()
    }.sum())

}

fun String.replaceLast(oldValue: String, newValue: String, ignoreCase: Boolean = false): String {
    val index = lastIndexOf(oldValue, ignoreCase = ignoreCase)
    return if (index < 0) this else this.replaceRange(index, index + oldValue.length, newValue)
}
val numbers = listOf(1 to "one",2 to "two",3 to "three",
    4 to "four",5 to "five",6 to "six",7 to "seven",8 to "eight",9 to "nine",0 to "NULL")

