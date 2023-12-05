package workspace.util

fun List<String>.tail(): List<String> {
    when (this.size) {
        0 -> throw IndexOutOfBoundsException()
        else -> return this.subList(1, this.size)
    }
}