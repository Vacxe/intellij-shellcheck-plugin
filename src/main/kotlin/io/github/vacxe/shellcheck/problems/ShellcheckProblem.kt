package io.github.vacxe.shellcheck.problems

import com.intellij.analysis.problemsView.FileProblem
import com.intellij.analysis.problemsView.ProblemsProvider
import com.intellij.codeHighlighting.HighlightDisplayLevel
import com.intellij.openapi.vfs.VirtualFile
import javax.swing.Icon

class ShellcheckProblem(
    override val provider: ProblemsProvider,
    override val file: VirtualFile,
    private val finding: Finding
): FileProblem {

    override val text: String
        get() = finding.message

    override val icon: Icon
        get() = when (finding.level) {
            Level.ERROR -> HighlightDisplayLevel.ERROR.icon
            Level.WARNING -> HighlightDisplayLevel.WARNING.icon
            Level.INFO -> HighlightDisplayLevel.WEAK_WARNING.icon
        }

    override val line: Int
        get() = finding.line- 1

    override val column: Int
        get() = finding.column - 1
}