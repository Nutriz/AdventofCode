import java.io.File

fun main(args: Array<String>) {
    //day1()
    //day2Part1()
    day2Part2()
}

// TODO function to get list of data from file

fun day2Part2() {

    val inputs = mutableListOf<String>()
    File("inputs/2.txt").useLines {
            lines -> inputs.addAll(lines)
    }

    val testData = listOf("abcde", "fghij", "klmno", "pqrst", "fguij", "axcye", "wvxyz") // fgij

    inputs.forEachIndexed { indexOne, lineOne ->
        inputs.forEachIndexed { indexTwo, lineTwo ->

            if (indexOne != indexTwo) {
                val diffCounter = mutableListOf<Int>()
                for (i in lineOne.indices) {
                    if (diffCounter.size < 2) {
                        if (lineOne[i] != lineTwo[i]) {
                            diffCounter.add(i)
                        }
                    }
                }

                if (diffCounter.size == 1) {
                    println("found: $lineOne and $lineTwo")
                    println("result 2b: ${lineOne.replaceRange(diffCounter[0], diffCounter[0]+1, "")}")
                }
            }
        }
    }
}

fun day2Part1() {

    fun String.letterMap() = this.groupingBy { it }.eachCount()

    val inputs = mutableListOf<String>()
   File("inputs/2.txt").useLines {
           lines -> inputs.addAll(lines)
   }

    val testData = listOf("abcdef", "aabbbcdef", "aaabcdef")

    var twoCounter = 0
    var threeCounter = 0

    inputs.forEach { line ->
        var twoFound = false
        var threeFound = false
        line.forEach {
            if (!twoFound && line.count { char -> char == it } == 2) {
                twoFound = true
                twoCounter++
            }

            if (!threeFound && line.count { char -> char == it } == 3) {
                threeFound = true
                threeCounter++
            }
        }
    }

    println("result b1: ${twoCounter * threeCounter}")


    // Variant more idiomatic for part 1 from Joris Portegies Zwart
    // https://github.com/jorispz/aoc-2018/blob/master/src/commonMain/kotlin/P02.kt
    val boxIDs = testData.asSequence()
    val twos = boxIDs.count {
        it.letterMap().containsValue(2)
    }
    val threes = boxIDs.count {
        it.letterMap().containsValue(3)
    }

    println("alt result 2a: ${twos * threes}")
}

fun day1() {

    val inputs = mutableListOf<Int>()
    val result = File("inputs/1.txt").useLines { lines ->
        // lines.sumBy(String::toInt)
        lines.forEach { inputs.add(it.toInt()) }
    }

    // first part
    println("result 1a: $result")

    // second part
    val testData = listOf(-6, +3, +8, +5, -6) // result must be 5

    var sum = 0
    val sumArchive = mutableListOf<Int>()
    while (true) {
        inputs.forEach {
            sum += it
            sumArchive.add(sum)
            if (sumArchive.count { value -> value == sum } == 2) {
                println("found $sum")
                return
            }
        }
    }
}