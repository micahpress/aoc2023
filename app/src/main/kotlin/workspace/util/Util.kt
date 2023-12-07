package workspace.util

fun List<String>.tail(): List<String> {
    when (this.size) {
        0 -> throw IndexOutOfBoundsException()
        else -> return this.subList(1, this.size)
    }
}

fun max(a: Double, b: Double): Double {
    return if (a > b) a else b
}

fun min(a: Double, b: Double): Double {
    return if (a < b) a else b
}
