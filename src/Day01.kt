import java.io.File

val Day01 = fun() {

    val inputs = mutableListOf<Int>()
    val result = File("inputs/1.txt").useLines { lines ->
        // lines.sumBy(String::toInt)
        lines.forEach { inputs.add(it.toInt()) }
    }

    // first part
    println("result 1a: $result")

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

// result must be 5
// part 2 result must be 5
val testData01 =
"""-6
+3
+8
+5
-6"""