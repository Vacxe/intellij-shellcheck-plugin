package io.github.vacxe.shellcheck

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.ExternalAnnotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiFile
import io.github.vacxe.shellcheck.problems.Finding
import io.github.vacxe.shellcheck.problems.Level

class ShellcheckAnnotator : ExternalAnnotator<PsiFile, List<Finding>>() {
    override fun collectInformation(file: PsiFile): PsiFile = file

    override fun doAnnotate(collectedInfo: PsiFile): List<Finding> {
        if (
        //!collectedInfo.project.isShellcheckEnabled() ||
            !isShellFile(collectedInfo)
        ) {
            return emptyList()
        }

        val service = ConfiguredService(collectedInfo.project)
        return service.execute(collectedInfo, autoCorrect = false)
    }

    private fun isShellFile(psiFile: PsiFile): Boolean =
        SHELL_FILE_EXTENSIONS.contains(psiFile.virtualFile.extension)

    override fun apply(
        file: PsiFile,
        annotationResult: List<Finding>,
        holder: AnnotationHolder,
    ) {
        val linesLength = file.text.split("\n").map { it.length + 1 }

        for (finding in annotationResult) {
            val textRange = TextRange(
                findPosition(linesLength, finding.line, finding.column),
                findPosition(linesLength, finding.endLine, finding.endColumn)
            )
            val message = buildString {
                append("Shellcheck - ")
                append(finding.code)
                append(": ")
                append(finding.message)
            }
            val severity = getSeverity(finding)
            val annotationBuilder = holder.newAnnotation(severity, message)
                .range(textRange)

            if (textRange == file.textRange) {
                annotationBuilder.fileLevel()
            }

            annotationBuilder.create()
        }
    }

    private fun getSeverity(finding: Finding): HighlightSeverity {
        return when (finding.level) {
            Level.ERROR -> HighlightSeverity.ERROR
            Level.WARNING -> HighlightSeverity.WARNING
            Level.INFO -> HighlightSeverity.WEAK_WARNING
        }
    }

    private fun findPosition(linesLength: List<Int>, line: Int, column: Int): Int {
        return linesLength.subList(0, line - 1).sum() + column - 1
    }
}