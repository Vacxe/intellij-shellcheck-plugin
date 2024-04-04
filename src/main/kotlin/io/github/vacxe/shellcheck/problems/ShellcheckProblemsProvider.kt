package io.github.vacxe.shellcheck.problems

import com.intellij.analysis.problemsView.ProblemsProvider
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
class ShellcheckProblemsProvider(override val project: Project) : ProblemsProvider {

    override fun dispose() {
    }
}