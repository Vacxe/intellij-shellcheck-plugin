package io.github.vacxe.shellcheck.problems

import com.intellij.analysis.problemsView.Problem
import com.intellij.analysis.problemsView.toolWindow.ProblemsViewPanel
import com.intellij.analysis.problemsView.toolWindow.Root
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class FindingsRootNode(private val project: Project, panel: ProblemsViewPanel) : Root(panel) {

    override fun getFileProblemCount(file: VirtualFile): Int = getFileProblems(file).size

    override fun getFileProblems(file: VirtualFile): Collection<Problem> {
        val provider = project.service<ShellcheckProblemsProvider>()
        return FindingsManager.getInstance(project).getFindings(file)
            .map { ShellcheckProblem(provider, file, it) }
    }

    override fun getOtherProblemCount(): Int = getOtherProblems().size

    override fun getOtherProblems(): Collection<Problem> = emptyList()

    override fun getProblemCount(): Int = FindingsManager.getInstance(project).getAllFindingsSize()

    override fun getProblemFiles(): Collection<VirtualFile> = FindingsManager.getInstance(project).getAnalyzedFiles()
}
