package advent.year2023

fun main() {

    // Create a list containing maps from the input
    fun splitMaps(maps: List<String>): List<List<String>> =
        if (maps.isEmpty()) listOf()
        else maps.takeWhile { !it.isEmpty() }.let { map ->
            listOf(map) + splitMaps(maps.drop(map.size + 1))
        }

    // pivot a string matrix
    fun pivot(matrix: List<String>) =
        (0 until matrix[0].length).map { x ->
            (matrix.indices).map { y ->
                matrix[y][x]
            }.joinToString("")
        }

    // Find first reflection point in map
    fun reflect(matrix: List<String>, start: Int = 0, pivot: Boolean = true): Int =
        // Find first possible reflection point from a given start point
        (matrix.drop(start).windowed(2).indexOfFirst { (a, b) -> a == b } + start)
            .let {  location ->
            if (location >= start) {
                // Validate if everything reflects from this line, if not continue searching for reflection points.
                if ((location downTo 0).any { counter ->
                        !if (location + (location + 1 - counter) > matrix.size - 1)
                            true
                        else {
                            matrix[counter] == matrix[location + (location + 1 - counter)]
                        }
                    }) {
                     reflect(matrix, location + 1, pivot)
                } else {
                    // We found a mirrored line, now, what to return?
                    if (pivot) (location + 1) * 100 else location + 1
                }

            } else {
                if (!pivot) throw Exception("Should have been found")
                else reflect(pivot(matrix), 0, false)
            }

        }

    println(splitMaps(day13input.split("\n")).sumOf { matrix ->
        reflect(matrix)
    })

}

val testInput = """
    #.##..##.
    ..#.##.#.
    ##......#
    ##......#
    ..#.##.#.
    ..##..##.
    #.#.##.#.

    #...##..#
    #....#..#
    ..##..###
    #####.##.
    #####.##.
    ..##..###
    #....#..#
""".trimIndent()

val day13input = """
    ######..#
    .##....#.
    .....##.#
    .....##.#
    .##..#.#.
    ######..#
    #..#..###
    .##..#.##
    #####.#.#
    #..#.##..
    .....###.
    #####....
    ....##.#.

    ##..###.###..
    ##.#.#..##..#
    ..#.#...#.#.#
    ######.#.####
    ##..#.##....#
    ##.##.#..#.#.
    #####....#.#.
    ###.########.
    #######..#.##
    ..####..##..#
    ......#.###..
    ......#.###..
    ..####..#...#

    ....#....##..##..
    ##.#.....#....#..
    ....##...#.##.#..
    ..##..#####..####
    ###..##..######..
    .....##.#.#..#.#.
    .#.#####.##..##.#
    ###.###...####...
    ....####.#....#.#

    #.##.#.####.#
    .#..#..#..#..
    .####.#....#.
    #....#......#
    .#..#.#....#.
    .##.#.######.
    #....########
    #....##....##
    ########..###
    .#..#..####..
    .#..#..####..
    ..##....##...
    .####...##...
    .#..#.##..##.
    ##..#########

    #.##.#.##
    #....##..
    .......##
    #....##..
    #....####
    #.####.##
    .####.###

    .###.####
    .##.##..#
    .###.####
    #....#..#
    ....#....
    ##.##....
    ##.###..#
    #..#..##.
    .#...#..#
    ....#....
    ##.######
    ...###.##
    #.###....
    ##.#.####
    ##.#.####

    ......##.##
    ####..###..
    .##....#.#.
    ######.####
    ######.####
    .##....#.#.
    ####..###..
    ......##.##
    .##.##.##.#
    ...##..###.
    ....###.##.
    #..#......#
    ....##.....
    #####.#.#..
    .....#..##.

    ###.....###..##
    #.#..#..##...##
    ..##.#...###.##
    ..##.#...##..##
    #.#..#..##...##
    ###.....###..##
    .##.#...#.#...#
    ##...#####.###.
    ##.#...###....#
    #.####..#.#.#..
    #.##..###.###..
    #.##..###.###..
    #.####..#.#.#..
    ##.#...###....#
    ##...#####.###.

    ..###..#....#..
    ##.....#.##.#..
    ###....#.##.#..
    ###.#..#....#..
    ..##.#........#
    ####...........
    ..##...##..##..
    ##...#.######.#
    ..#.#.########.
    ##....#.####.#.
    ##.#.####...###
    ##...#.#....#.#
    ##...##.#..#.##

    ##..#..#..##..#
    .###....###.#..
    .#...##...#..#.
    ..##.##.##.....
    .#.#.##.#.#.#.#
    .....#........#
    .#..####..#.##.
    ####.##.####.##
    ####.##.####.##

    .#.#..####.
    #.####.##.#
    ..#..######
    ..#...####.
    ..#...####.
    ..#..######
    #.####.##.#
    .#.#..####.
    #..#.##..##
    #.#.#######
    ###........
    ....##....#
    ..#.###..##
    ...#..#.##.
    .#.#.##..##

    ##...#...##.#.#.#
    ##.#.......#.#.##
    ##.#.......#.#.##
    ##.......##.#.#.#
    ###....#.##.#...#
    .#.#.#..#.###.##.
    .#...#..##.......
    #..#.#....#.##.##
    .#.##...###.#..##
    .##.#.#.#.#...###
    #...##..#...#.##.
    .#.####...#..#.##
    .#.####...#..#.##
    #...##..#...#.##.
    .##.#.#.#.#...###

    .....#.##..##.#
    .....#.##..##.#
    .##.##.##..##..
    ....#..#....#..
    #..#.###....###
    ##..#.##.##.##.
    #...##........#
    .#..#.###..###.
    ..##...#....#..
    #....#.##..##.#
    #.##..###..###.

    .##.....#..#.
    .##..#.######
    #.#...#......
    .###..#......
    ..#..#..#..#.
    ..#..#..#..#.
    .###.##......
    #.#...#......
    .##..#.######
    .##.....#..#.
    ........####.
    .####.##....#
    .#.##.#......
    ######..####.
    .#.##..##..##
    ....##.#....#
    .###.#.######

    ###.#.####.#.
    ###.#.####.#.
    ...#.#.##.#.#
    ###...#..#...
    .#.#.#....#.#
    ##..#..#...#.
    ..###..##..##
    ####..#..#..#
    #...#......#.
    ..##..####..#
    .#..########.
    .#.#.#....#.#
    #..####..####
    .#...........
    #.#.#.#..#.#.

    #.#....
    #.#....
    #..#..#
    ..##..#
    #......
    #......
    ..#.##.
    #.#.##.
    #.##...
    ..#.##.
    #.#.##.
    #.#####
    #.#####
    .#.####
    ##..##.

    .##.....###
    #..###.##.#
    .##..#.##..
    .....#.#.#.
    #..##.#..##
    #..##.#..##
    .....#.#.#.
    .##..#.##..
    #..###.##.#
    .##..#..###
    #..##..##.#
    ####...##.#
    #..#.#.#.#.
    ........#.#
    ....####...
    ....#.###.#
    .##.#.#..#.

    ###.######.
    ...##.##.##
    .###.#..#.#
    ....######.
    .#.########
    #####....##
    ..##..##..#
    #.##..##..#
    ##....##...
    .#..#......
    #####....##
    ##....##...
    .##.######.
    ###..####..
    ....#.##.#.
    ....#.##.#.
    ###..####..

    #.####..####.##..
    ##..##..##..##.##
    .#....##....###..
    ###...##...###.##
    #.#........#.#.##
    .#.##....##.#.###
    .#..........#....
    .#..........#.###
    ##.##.##.##.#####
    ##..........#####
    .#..........#.###
    .#####..#####....
    #..##....##..##..

    #.##.#.####
    .###.######
    #..#....##.
    #...#######
    ##.##..#..#
    ...#.######
    #.##.......
    .#.........
    ##.####.##.
    #..####.##.
    .#.........
    #.##.......
    ...#.######
    ##.##..#..#
    #...#######
    #..#....##.
    .###.######

    .#...###...
    .....##.###
    ..#..##.#..
    ####.......
    ..##..#.##.
    ######.###.
    ########..#
    ########..#
    ######.###.
    ..##..#.##.
    ####.......

    ##....###..
    ###..####..
    ..#..#..##.
    ...##...#.#
    .#.##.#.##.
    ##.##.####.
    ########.##
    ..#..#..##.
    ########.#.
    ##.##.##...
    ...##...###
    #########..
    ...##....##

    #.....###....
    #.##.####.##.
    #..#..##..#..
    ##...#..#...#
    .....#..#....
    ####..##..###
    ..##......##.
    ##..######..#
    ##.##....##.#
    ##.##....##.#
    ##..######..#

    .####.##.#.#..##.
    .....######..#..#
    ..#..######..#..#
    .####.##.#.#..##.
    ######..##...####
    #..##.#..#.######
    ..#....#.##......
    .##.#..###...####
    .....#..#..##....
    ##.#..##.###..##.
    #..##.#.##.#.#..#
    ..##.#.##...##..#
    ####.......#.....
    .###.####...##..#
    #..#..#.#####....
    ###.#.#..###.....
    ####...######....

    #####.###...#
    ##..#.#.###..
    #..#..##..#.#
    ##...##.#..#.
    #####....##.#
    .....##...###
    #####.#.#.###
    #####.#.#.###
    .....##...###

    ..#####
    .#.....
    .######
    #..####
    .######
    ..#....
    ###....
    ###....
    ##.####
    ..#.##.
    .#.####
    .######
    .#.####
    #.#####
    ##.....

    ####....#...#.###
    .#..#..####.##..#
    .##.##.##..#####.
    ......##.##.#####
    ###.#.##.#####.##
    .####....#...####
    .##.#....#...####
    ###.#.##.#####.##
    ......##.##.#####
    .##.##.##..#####.
    .#..#..####.##..#
    ####....#...#.###
    ####....#...#.###

    ###...#..#...##
    ...#......#.#..
    ....#......#...
    ##..#.####.#..#
    ##..#.#..#.#..#
    ##.#.######.#.#
    ....#.####.#...
    ..##.#.##.#.##.
    ##...######...#
    ###.#..##..#.##
    ..#..######..#.

    .##..##..##..####
    ####.##.####.....
    .###.##.###..####
    ..#.#..#.#...####
    .............#..#
    ...######...#.##.
    ..#.#..#.#..####.

    ##.##.#.#
    ###.#.#.#
    ###.#.#.#
    ##.####.#
    ...#####.
    ...######
    ...#..###
    ..######.
    ##...##.#
    ##...#..#
    #####.#..

    #.###..
    ####.##
    .#.##..
    ....###
    .##....
    .##....
    .#..###

    ......#....#.....
    #..#.########.#..
    .##.##......##.##
    ....#.######.#.#.
    .#..##########..#
    #.#..###..###..#.
    ####..######..###
    #.##..#.##.#..##.
    #..#..######..#..
    .#.#..##..##..#.#
    #.#...#.##.#...#.
    .#.##.#.##.#.##.#
    .#.##.#.##.#.##.#

    ###..##
    #....##
    ###..#.
    ##.....
    ..#..##
    #....##
    #...###
    ###.###
    ..##.##
    ...##..
    ...##..
    ..##...
    ##.#...
    ..#....
    .##.###
    ###....
    ###....

    ####.###..#
    .......###.
    ......####.
    ####.###..#
    ###..#.####
    ###.#....##
    ..#.#.....#
    ###.##.#.##
    ##.###.###.
    ####.##.#..
    ##..#.#.#..

    #..##.....##.
    .......#..#.#
    .##..#...#.##
    .##..#...#.##
    .......#..#.#
    #..##.....##.
    .##..#.######
    ######.##....
    ####...##....
    ....#.##.#.##
    #.....####...
    .##..###.....
    #####..#....#

    #..####..#...
    ####..#####..
    ###....###.#.
    ..#....#..#..
    .#.#..#.#.###
    .###..###....
    ###########..
    #...##...#.##
    ...........##
    ..#....#...##
    #.#....#.####

    .##...#.#
    .##..#...
    .##.###..
    .##..#.#.
    .##....##
    #####.##.
    .##...###
    #..#..#.#
    #..#..#.#
    .##...###
    ########.
    .##....##
    .##..#.#.
    .##.###..
    .##..#...

    .##.#....###..#
    .##.#....###..#
    ..#######......
    .######.#.##.##
    ..#.#..#...###.
    ##....#.#.###..
    ##....#.#.###..
    ..#.#..#...###.
    .######.#.##.#.
    ..#######......
    .##.#....###..#

    .##..####..
    .##..####..
    ###...##...
    ...###..###
    ..#.#.##.#.
    ...######.#
    ...#..##..#
    ...#.#..#.#
    #.#.##..##.

    ##.#.........
    #.####..##..#
    ##.#.########
    ##.##........
    ##.##########
    #.#..........
    .#.#.........
    #.###########
    .###.#......#
    ###..........
    #.#.#.##..##.
    .#....##..##.
    #.#...##..##.
    .##.##..##..#
    .#.#.#..##..#

    ...####
    ...####
    .#..#..
    ######.
    ##.#..#
    ##.#.##
    ######.
    .#..#..
    ...####

    .....#.
    ###.##.
    ##...#.
    ####.##
    ####.##
    ##...#.
    ###.##.
    .....#.
    #.##...
    ###..#.
    ###.###
    ....##.
    ##.#.##

    ##.#....#
    ##.#.##.#
    ...#....#
    ..#.####.
    ....####.
    ##...##..
    ....####.
    ####.##..
    ..#......
    ##...##..
    ###.####.
    #####..##
    ###..##..

    ..##..###
    ..##.##..
    ..##.##.#
    ..##..###
    ##.....##
    .....##..
    ..##...#.
    ##.#...#.
    ##.###..#

    .####...##...
    ..#....#..#..
    #####.##..##.
    #..#.##.##.##
    ...##.#.##.#.
    ####...####..
    #.......##...
    #..#....##...
    ####...####..

    #.##..#
    ...####
    ...####
    #.##..#
    ...##..
    ###.#..
    ###..#.
    .....##
    ##.#...
    #.#..##
    .##...#
    .##...#
    #.##.##

    .##...##.##.##...
    .#.####..##..####
    .#.####..##..####
    .##...##.##.##...
    ####.##.####.##.#
    ##.#####....#####
    #.###..#....#..##
    ...##..........##
    .#.###.######.###
    #.##.####..####.#
    ..##.....##.....#
    .#..#...#..#...#.
    #.##...##..##...#
    #.#.#.##.##.##.#.
    .#..####....####.
    #...####.##.###..
    ...#..#.####.#..#

    ....#.##..#.#.##.
    #..##..#..#.#...#
    .##.##.#.#..#.##.
    .....#....#.#.##.
    ####.###....#....
    #..#.#.#.#..#....
    #..###.##....####
    ####..#....#.#..#
    ####.#...###.#..#
    #####..#.###.....
    ####.#####.#.#..#
    #####.#.###...##.
    ####.##..#...####

    .......##......
    ....########...
    #####.####.####
    ##..#.####.#..#
    ##.##......##.#
    ....###..###...
    ...#..#..#..##.

    ..........###..
    #.####.##..#...
    #.#..#.##.#...#
    .######.###..##
    ...##...##.####
    ########..##...
    ............#..

    ....#.###.#..
    #..#...#...#.
    ....#.##.####
    ....#.##.####
    #..#...#...#.
    ....#.###.#..
    ......##.####
    .###.#####.##
    ####.#####.##
    ....#.##....#
    ....##..#..#.
    .##.###....#.
    .##....####..
    #####...#.#..
    ######.#..###

    .##.###
    ...#...
    ###.###
    .#.....
    #.#.#..
    ####...
    ..##...
    ##.#...
    #......
    .#.##..
    #.#.###
    ..#####
    ..#####
    ..#.###
    .#.##..

    ####.#.####
    ####.#..##.
    #...#.##..#
    #...#.##..#
    ####.#..##.
    ####.#.####
    #.#...#....
    #.....##..#
    #.#.##..##.
    .###.#..##.
    ##...#.....
    .###..#....
    .####.#.##.
    ....#...##.
    .####....#.

    ....#####
    ....#....
    ####.#..#
    .....####
    #..######
    #..#.....
    #####....
    ###.#####
    #####.##.
    #..##....
    .##.#....
    ####.####
    #..#.####

    ######..##.#.#.##
    #.........####...
    ###.##.....#.##..
    ##.#.###..###..##
    ###..#...#....###
    #.#...#.#######..
    .#..#.###.#.##...
    .##...####.#..###
    .##....#....#.#..
    .#.##.#..#..#####
    ##....###......##
    ...##.######..#..
    ....#.#..#.######
    ##...###..##..###
    ##.#.###..##..###

    .....###..###.#..
    #####.#...#......
    #####.##.#..#....
    .##.##.#.###.#.##
    ......#..###.##..
    ....#...#.####...
    .....####.#.###..
    ......#.##.##.#..
    ...##.##....#.#..
    #..##.##.###..###
    #..#..###.###..##
    #..#...#....###..
    ....#....##.#..##

    ....#....
    ##.######
    ##.#.....
    ##.#.####
    ##..#...#
    ..#.#.##.
    ......##.
    ###..####
    ..##.#..#

    ..##..#
    ##.....
    ...####
    ...####
    ##.....
    ..##..#
    #.#.#..
    #.##...
    #..##.#
    ####...
    ####...
    #..##.#
    #.##...
    ..#.#..
    ..##..#

    ...##.#######
    ...##.#.#####
    #....#..#..##
    #..########..
    #.##...#...##
    .#....#...#..
    .#....#...#..
    #.##...#...##
    #..########..
    #....#..#..##
    ...##.#.#####
    ...##.#######
    .####.....###
    ######.#.#..#
    ..#..#.#.####
    ...#..#..####
    ..####..###.#

    ########.
    .......##
    .####.#..
    ..##...##
    #....#.#.
    ..##....#
    ######...
    .#..#..##
    ##..###..
    ......##.
    ..##..##.
    #.#..##..
    .#..#..##
    #....##.#
    #....##.#

    ######.####
    #.##.######
    .#..#.##..#
    ...#..##..#
    ######.#..#
    .####...##.
    .......####
    ######.#..#
    #....##....
    .####.#....
    ......#####
    ######.####
    #....##.##.

    .####.###.#
    .#..#......
    ##..#######
    #.##.#..###
    #######..##
    ######.###.
    ##..##...#.
    ##..##.#.#.
    ######.###.

    .##...##..#.#
    #..#.#..#....
    .##.##.##..#.
    .##..#.#.###.
    .....#.#.#...
    .##.#.###...#
    ####..###...#
    ####..###...#
    .##.#.###...#
    .....#.#.#...
    .##..#.#####.

    #.#.#...###
    #.....#.#..
    ###.##.##..
    ######.####
    ####.#.#..#
    ###..##..#.
    .###..#.#.#
    .###..#.#.#
    ###.###..#.
    ####.#.#..#
    ######.####
    ###.##.##..
    #.....#.#..
    #.#.#...###
    ####...####
    ####...####
    #.#.#...###

    ###...#...#.##.
    .#######.#.####
    .#######.#.####
    ###...#...#.##.
    #.#####...#..#.
    ##..#..##..#..#
    #..##.##..##..#

    #..#..#..######..
    #.#.##.#.#.##.#.#
    ..##..##...##...#
    ....##...........
    .########.#..#.##
    #...##...######..
    .#......#.#..#.#.
    #.##..##.######.#
    ....##.....##....
    ##......##....##.
    ...####...#..#...
    #.#....#.#.##.#.#
    ..#.##.#........#
    #####.####....###
    .###..###.#..#.##
    ##......##....##.
    ..#.##.#..####..#

    ##.########
    .#..#....#.
    ####.#..#.#
    #.##.#..#.#
    .#..#....#.
    ##.########
    #.#..#..#..
    #..#..##..#
    ##...####..
    .##########
    .#.########
    ..###.##.##
    .#....##...
    ##...####..
    ##..#....#.
    ##..######.
    ..#.##..##.

    .#..#.#.#.###.##.
    .#..#.#.#.##..##.
    ......####....#..
    .#..#...##..####.
    ######.###...####
    .#..#..#....#.#.#
    ......##...#.....
    #######.#.#######
    .#..#..##..##.##.

    .###.#.##......
    .......###.##.#
    .##..#..#.#..#.
    #..##.####.##.#
    ####.#..###..##
    #..##.....####.
    #..#...##.#..#.
    ....#.....#..#.
    ####..###......

    #.#..##.####.
    #.#..##.####.
    .##..#.#.....
    #.###.#..#...
    ....#.####..#
    ...#.##..###.
    .###..#.#....
    ..#....#.....
    .#.##..#..##.
    .#.##..#..##.
    ..#....#.....
    .###..#.#....
    ...#.##..###.
    ....#.####..#
    #.###.#..#...
    .##..#.#.....
    ..#..##.####.

    ...######..
    ####.#..#.#
    .#...#..#..
    #..###.#...
    #..###.#...
    .#...#..#..
    .###.#..#.#
    ...######..
    ..##..#..#.
    ..##.##.##.
    ##.#####..#
    ....#.###.#
    #########..
    #.#...##...
    #.#...##...
    #########..
    ....#.###.#

    ##....####..###..
    ########..#.##...
    ########..#.##...
    ##....####..###..
    ........####....#
    .#.##.#.##.#..#.#
    ........##.######
    .##...#....#.###.
    .######....#.#..#
    .#....#.#..###.#.
    .##..##...#...##.

    ###.##.....
    #..#.###.##
    #..#..##...
    #.#..#.##..
    .##.###.##.
    ###....####
    .##..#.#...
    .##..#.#...
    ###....####
    .##.###.##.
    #.#..#.##..
    #..#..##...
    #..#.###.##
    ###.##.....
    ###.##....#

    #..#.....#.####.#
    ####.#.#..#....#.
    #####..#.........
    #.#####.##..##..#
    #..#.#.#...####..
    #..#.##....####..
    #..#####.###..###
    .##.#....#..##..#
    #..##.#.##.#..#.#

    ##..#..#.##.##.##
    ##..#..#.##..#.##
    .#####..##.##..##
    ..#.###.###..#..#
    ..#.###.###..#..#
    .#####..##.##..##
    ##..#..#.##..#.##
    ##..#..#.##.##.##
    #...###..#.##...#
    #........#..#.##.
    #...#.#...#.##...

    #....##.#..
    #.##.#..###
    .####.##..#
    .####.##..#
    #.##.#...##
    #....##.#..
    ##..##.#..#

    .#.#..#.#.#.#..##
    .##....##..#.####
    ####..####..#.#..
    .##.##.##.#...#.#
    ..######..##.....
    ..######..##..#..
    .##.##.##.#...#.#

    .####.#
    .####..
    .####.#
    #....#.
    #.##.##
    ##..###
    .####..

    #..#.#..###...##.
    #..#.#..###...##.
    #.#.....##.#.##.#
    #..#####...#.#.##
    ##....###......##
    ##..###.#.###..##
    .#....###...#..##
    #.###.#...#.....#
    ##.....#...##.###
    ......#..########
    ##.#############.
    .#.#.##.....####.
    ###.##.###.###..#
    ####.###..#.#.#..
    #.##.#......#.##.
    #####.....#..#..#
    #####..#..#..#..#

    .##.####.##....##
    ####.##.######...
    #.#..##..#.#...##
    ##.#....#..#.#...
    ...#.##.#....#...
    #..........#.....
    #.##....##.#..###
    #..#.##.#..##.###
    ............#.###
    ...#.##.#...#..##
    #.##.##.##.#.#...
    ...#.##.#........
    ...######....#...
    #.#.####.#.##.#..
    ##.#....#.####.##
    ...#.##.#....####
    .#.#....#.#...###

    ...##.##..####.
    ..##.#.#.#.####
    ##.#.#.###.##..
    ......#.#####..
    ..##..#.####.##
    ..##...#.#.#...
    ..#.#.#...#....
    #####.##.##.#..
    ##.###.##......
    ..#..##..#.##..
    ....#....##.#..
    ...#.#.#.#.####
    ##.#.##.#.##.##
    ###..#....###..
    ..##.#..#.#..##
    ..##..#.##..###
    ..#..#.##.###..

    .....##..#.#.##.#
    #..####.#.##..##.
    ..##..##.#......#
    .##.###.###....##
    #..#.#..#........
    ..##.##..###..###
    .##....#.#..##..#
    ##.#.##..........
    ##.#.##..........

    #..##....
    #....#.#.
    ##.###.##
    .####.###
    #.#######
    ..#.###..
    ..#.###..

    .#.###.#...
    #.#.#.#..##
    ###..#..#..
    #.#..#..#..
    #.#.#.#..##
    .#.###.#...
    #......####

    #.#..#..#..#.##
    .#.########.#..
    .#...##.#...#..
    .#.#......#.#..
    ###...##...####
    .#..........#..
    ...#......#....

    ...#.#####.#.
    .#.#.....#..#
    #.##..#.#.###
    #.##..#.#.###
    .#.##....#..#
    ...#.#####.#.
    ######......#
    ###.####.####
    ..##.###.###.
    ###......##..
    ...##.#....#.
    ...##.#....#.
    ###......##..
    ..##.###.###.
    ###.####.####
    ######......#
    ...#.#####.#.

    ##.#....#.#
    .#.##..##.#
    ..###...##.
    #..######..
    #...#..#...
    #..#....#..
    .###.##.###
    .####..####
    #.##.##.##.
    #..######..
    ...#.##.#..
    ...#.##.#..
    #..######..
    #.##.##.##.
    .####..####
    .###.##.###
    #..#....#..

    ####.#.####.####.
    #.#.#..######..##
    .##.##.....#....#
    .##.####...######
    ...#..#.#.###..##
    .####.###.###..##
    #.##..#.#....##..
    #....##.#..######
    #....###..##.##.#
    #....####.##.##.#
    #....##.#..######
    #.##..#.#....##..
    .####.###.###..##
    ...#..#.#.###..##
    .##.####...######
    .##.##.....#....#
    #.#.#..######..##

    .#.####.#.#....
    .#.####.#.#....
    .##......##...#
    .#...#....#.#..
    ###..#.#..#.#.#
    ####....#.##..#
    .#.#..##.#.##.#
    .##....###..#.#
    ..##.##.##...#.
    .#.#.....#....#
    .#.#.....#....#
    ..##.##.##...#.
    .##....###..#.#
    .#.#..##.#.##.#
    ####....#.#...#

    ....#..
    #.##..#
    #####..
    .##.##.
    ####.##
    ####.##
    #..###.
    #..##..
    #####..
    .....##
    .....##

    ##.#....#..#...#.
    ####..#..#####...
    ####..#..#####...
    ##.#....#..#...#.
    ####.##.#.#.#...#
    #####....##.####.
    .#####..###.#..#.
    ######.##...#.#..
    ...###.....#.####
    ###.##..##..####.
    ..#.##.......####

    ##.#..#.#.#
    ##.##.#.#.#
    ####.#..#.#
    ..##.####..
    ##.####.#..
    ....##..#.#
    ..##.###.#.
    #####.###..
    ######....#
    ##.#####.##
    ..#.##.#.#.

    .##...#
    ....###
    ####.##
    ####.##
    ....###
    .##...#
    .....#.
    #..#...
    .##.#..
    ...##.#
    ....#..

    #######..####
    ####.##..#...
    #..##..#...#.
    .#####....###
    .##.....##...
    ....#.###..##
    #..#..##..##.
    ........###.#
    ........###.#

    ####...##
    ###..#...
    ###.##...
    ####...##
    #.#..#..#
    #.###....
    ....##.##
    .#...####
    #.#..#..#
    ###....##
    .....##..
    .....##..
    ###....##

    #.#...#..#...
    ...##.####.##
    ...##.####.##
    #.....#..#...
    .##..##..##..
    ...#...##...#
    #...#.####.#.
    ##...#.##.#..
    #..##......##
    #.##...##...#
    ##....####...

    #..###.##
    #..#.#.##
    ..##.###.
    ..##.#.##
    ##...##.#
    ##...##.#
    ..##.#.##
    ..##.###.
    #..#.#.##
    #..###.##
    .###..#.#

    ...#.###....#
    ..#.###.####.
    .#.#..#######
    .#####.#.##..
    #.#.#.#..##..
    ##.###.......
    ##.###.......

    #.##..##.##.#
    ...####......
    #..####..##..
    ##.####.####.
    ####..#######
    ##..##..####.
    ##..##..####.
    #..#..#..##..
    ..##..##....#
    ..#....#....#
    ...#..#......
    .##..#.##..##
    .#..##..#..#.
    #.#.##.#.##.#
    .########..##

    ##....##.####
    .#...##.###..
    .#.#.....#.#.
    .##..#.#.#.##
    .....#.##.#.#
    .....#.##.#.#
    .##..#.#.#.##
    .#.##....#.#.
    .#...##.###..
    ##....##.####
    .############
    ..####.###.##
    ..####.###.##
    .############
    ##....##.####
""".trimIndent()
