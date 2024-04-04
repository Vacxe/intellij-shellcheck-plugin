package io.github.vacxe.shellcheck.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.application.runReadAction
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.psi.PsiManager
import io.github.vacxe.shellcheck.ConfiguredService
import io.github.vacxe.shellcheck.SHELL_FILE_EXTENSIONS
import io.github.vacxe.shellcheck.ShellcheckBundle
import io.github.vacxe.shellcheck.problems.FindingsManager
import io.github.vacxe.shellcheck.utils.PluginUtils

class RunAnalysisAction : AnAction(PluginUtils.pluginIcon())  {

    override fun update(event: AnActionEvent) {
        val selectedFiles = event.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY) ?: return
        val isDirectoryOrKotlinFile = selectedFiles.any { it.isDirectory || it.extension in SHELL_FILE_EXTENSIONS }
        event.presentation.isEnabledAndVisible = isDirectoryOrKotlinFile
    }

    override fun actionPerformed(e: AnActionEvent) {
        val selectedFiles = e.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY)

        if (selectedFiles.isNullOrEmpty()) {
            return
        }
        val project = e.project ?: return
        val psiFiles = runReadAction {
            selectedFiles
                .mapNotNull { PsiManager.getInstance(project).findFile(it) }
                .toList()
        }

        val job = object : Task.Backgroundable(project, ShellcheckBundle.message("shellcheck.analysis.run"), true) {
            override fun run(indicator: ProgressIndicator) {
                indicator.checkCanceled()
                val service = ConfiguredService(project)
                val findingsManager = FindingsManager.getInstance(project)
                indicator.checkCanceled()
                for (psi in psiFiles) {
                    val findings = service.execute(psi, false)
                    indicator.checkCanceled()
                    findingsManager.put(psi.virtualFile, findings)
                }
                findingsManager.notifyListeners()
            }
        }

        ProgressManager.getInstance().run(job)
    }
}