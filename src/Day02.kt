import java.io.File

val Day02 = fun() {

    fun String.letterMap() = this.groupingBy { it }.eachCount()

    val inputs = mutableListOf<String>()
    File("inputs/2.txt").useLines {
            lines -> inputs.addAll(lines)
    }

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

val testData02 =
"""abcde
fghij
klmno
pqrst
fguij
axcye
wvxyz"""