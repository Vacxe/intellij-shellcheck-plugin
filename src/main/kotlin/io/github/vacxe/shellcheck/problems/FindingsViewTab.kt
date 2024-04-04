package io.github.vacxe.shellcheck.problems

import com.intellij.analysis.problemsView.toolWindow.ProblemsViewPanel
import com.intellij.analysis.problemsView.toolWindow.ProblemsViewState
import com.intellij.analysis.problemsView.toolWindow.ProblemsViewToolWindowUtils
import com.intellij.openapi.project.Project
import io.github.vacxe.shellcheck.ShellcheckBundle


private const val FINDINGS_TAB_ID = "io.github.vacxe.shellcheck.idea.findings.tab"
class FindingsViewTab(
    project: Project,
    state: ProblemsViewState,
) : ProblemsViewPanel(
    project,
    FINDINGS_TAB_ID,
    state,
    ShellcheckBundle.lazy("shellcheck.configuration.title"),
) {

    init {
        treeModel.root = FindingsRootNode(project, this)
        FindingsManager.getInstance(project).register {
            com.intellij.openapi.application.invokeLater {
                treeModel.structureChanged(null)
                ProblemsViewToolWindowUtils.selectTab(project, FINDINGS_TAB_ID)
            }
        }
    }
}