package workspace.day8

import java.io.File
import workspace.util.tail
import workspace.util.factor
import workspace.util.mergeButTake


class Day8Solver() {
    fun loadInput(inputPath: String): List<String> {
        return File(inputPath).readLines().filterNot { it == "" }
    }

    fun parseNodeString(arg: String): Triple<String, String, String> {
        val nodeLeftRight = arg.split(" = (", ", ", ")").filterNot { it == "" }

        return Triple(nodeLeftRight[0], nodeLeftRight[1], nodeLeftRight[2])
    }

    fun buildGraph(nodeStrings: List<String>): Map<String, Node> {
        val nodeMap: MutableMap<String, Node> = mutableMapOf()
        for ( nodeString in nodeStrings) {
            val nodeTriple = parseNodeString(nodeString)
            val leftChild = nodeMap.getOrPut(nodeTriple.second) { Node(nodeTriple.second, null, null) }
            val rightChild = nodeMap.getOrPut(nodeTriple.third) { Node(nodeTriple.third, null, null) }
            val node = nodeMap.getOrDefault(nodeTriple.first, Node(nodeTriple.first, null, null))
            node.leftChild = leftChild
            node.rightChild = rightChild
            nodeMap.put(node.value, node)
        }

        return nodeMap
    }

    fun getNextNode(currNode: Node, direction: Char): Node {
        return when (direction) {
            'R' -> currNode.rightChild!!
            'L' -> currNode.leftChild!!
            else -> throw IllegalStateException()
        }
    }
    
    fun countStepsToFindEndNode(rootNode: Node, directionSequence: String): Int {
        var stepsTaken = 0
        var dirStep = 0
        var currNode: Node = rootNode
        while (currNode.value != "ZZZ") {
            currNode = getNextNode(currNode, directionSequence[dirStep])
            dirStep = (dirStep + 1) % directionSequence.length
            stepsTaken += 1
        }
        return stepsTaken
    }

    fun part1(inputPath: String = "/workspace/inputs/day8.txt"): Int {
        val inputLines: List<String> = loadInput(inputPath)
        val directionSequence = inputLines.first()
        val nodeStrings = inputLines.tail()
        val graph = buildGraph(nodeStrings)

        return countStepsToFindEndNode(graph.getValue("AAA"), directionSequence)
    }

    fun findStepsToTerminalNodes(startNode: Node, directionSequence: String): List<Int> {
        var stepsTaken = 0
        var dirStep = 0
        var currNode: Node = startNode
        val dirStepsToNodes = mutableMapOf<Int, MutableSet<String>>()
        val stepsWithTerminalNodes = mutableListOf<Int>()
        while (currNode.value[2] != 'Z' || !dirStepsToNodes.getOrElse(dirStep) { setOf<String>() }.contains(currNode.value)) {
            if (currNode.value[2] == 'Z') {
                dirStepsToNodes.getOrPut(dirStep) { mutableSetOf() } += currNode.value
                stepsWithTerminalNodes.add(stepsTaken)
            }
            currNode = getNextNode(currNode, directionSequence[dirStep])
            dirStep = (dirStep + 1) % directionSequence.length
            stepsTaken += 1
        }
        return stepsWithTerminalNodes
    }

    fun part2(inputPath: String = "/workspace/inputs/day8.txt"): Long {
        val inputLines: List<String> = loadInput(inputPath)
        val directionSequence = inputLines.first()
        val nodeStrings = inputLines.tail()
        val graph = buildGraph(nodeStrings)
        val startNodes = graph.entries.filter { it.key[2] == 'A' }.map { it.value }

        return startNodes.map { findStepsToTerminalNodes(it, directionSequence) }
            .flatten()
            .map { factor(it) }
            .reduce { prevMap, nextMap -> prevMap.mergeButTake(nextMap) { _, exp1, exp2 -> listOf(exp1, exp2).max() } }
            .entries
            .fold(1.0) { acc, (factor, exp) -> acc * (Math.pow(factor.toDouble(), exp.toDouble())) }
            .toLong()
    }
}