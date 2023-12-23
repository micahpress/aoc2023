package workspace.day8

import java.util.Objects

data class Node(val value: String, var leftChild: Node?, var rightChild: Node?) {
    override fun toString(): String {
        return "$value (${leftChild?.value} ${rightChild?.value})"
    }

    override fun hashCode(): Int {
        return Objects.hash(value, leftChild?.value, rightChild?.value)
    }
}
