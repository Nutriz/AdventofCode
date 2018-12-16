import java.io.File

// inspired by https://github.com/jorispz/aoc-2018/blob/master/src/commonMain/kotlin/P03.kt
val Day03 = fun() {

    // example: #1 @ 1,3: 4x4
    data class Claim(val id: Int, val x: Int, val y: Int, val w: Int, val h: Int) {

        val points = (x until x + w).flatMap { x ->
            (y until y + h).map { y ->
                Pair(x, y)
            }
        }

        fun intersects(other: Claim): Boolean {
            if (id == other.id) return false

            val (left, right) = if (x < other.x) Pair(this, other) else Pair(other, this)
            val (top, bottom) = if (y < other.y) Pair(this, other) else Pair(other, this)
            return (right.x >= left.x && right.x <= left.x + left.w - 1) &&
                    (bottom.y >= top.y && bottom.y <= top.y + top.h - 1)
        }

        fun pointsInCommon(other: Claim) = points.intersect(other.points)
    }

    val claims = mutableListOf<Claim>()
    File("inputs/3.txt").useLines { line ->
        line.forEach {
            val values = it
                .removePrefix("#")
                .replace(" ", "")
                .split("#", "@", ",", ":", "x")
            val id = values[0].toInt()
            val x = values[1].toInt()
            val y = values[2].toInt()
            val w = values[3].toInt()
            val h = values[4].toInt()
            claims.add(Claim(id, x, y, w, h))
        }
    }

    val common = mutableSetOf<Pair<Int, Int>>()
    claims.forEachIndexed { index, claim ->
        claims
            .drop(index + 1)
            .forEach { other ->
                if (other.intersects(claim)) {
                    common.addAll(claim.pointsInCommon(other))
                }
            }
    }

    println("result 3a: Total square inch overlapping: ${common.count()}")

    val freeClaim = claims.first { claim ->
        claims.none { it.intersects(claim) }
    }

    println("result 3b: id ${freeClaim.id} ")

}

val testData3 =
"""#1 @ 1,3: 4x4
#2 @ 3,1: 4x4
#3 @ 5,5: 2x2"""