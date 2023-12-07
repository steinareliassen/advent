fun main() {
    // part 1
    println(
        mapOf(51 to 222, 92 to 2031, 68 to 1126, 90 to 1225).map { (time, win) ->
            (0..time).count { attempt ->
                (time - attempt) * attempt > win
            }
        }.reduce { x, y -> x * y }
    )

    // part 2
    println(
        mapOf(51926890L to 222203111261225L).map { (time, win) ->
            (0..time).count { attempt ->
                (time - attempt) * attempt > win
            }
        }.sum()
    )
}
