package io.github.vacxe.shellcheck.problems

data class Replacement(
    val line: Int,
    val endLine: Int,
    val precedence: Int,
    val insertionPoint: String,
    val column: Int,
    val endColumn: Int,
    val replacement: String
)
