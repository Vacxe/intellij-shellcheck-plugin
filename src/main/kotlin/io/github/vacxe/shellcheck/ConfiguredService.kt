package io.github.vacxe.shellcheck

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.progress.ProcessCanceledException
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import io.github.vacxe.shellcheck.config.ShellcheckPluginSettings
import io.github.vacxe.shellcheck.problems.Finding
import java.lang.reflect.Type

class ConfiguredService(private val project: Project) {

    private val logger = logger<ConfiguredService>()
    private val settings = project.service<ShellcheckPluginSettings>()
    fun execute(psiFile: PsiFile, autoCorrect: Boolean): List<Finding> {
        return runCatching { execute(psiFile) }
            .onFailure { logErrorIfAllowed(it, "Unexpected error while running shellcheck analysis") }
            .getOrDefault(emptyList())
    }

    private fun logErrorIfAllowed(error: Throwable, message: String) {
        if (error is ProcessCanceledException) {
            return
        }
        logger.error(message, error)
    }

    private fun execute(psiFile: PsiFile): List<Finding> {
        val process = ProcessBuilder("shellcheck", "--format=json", psiFile.virtualFile.path)
            .start()
        val result = String(process.inputStream.readAllBytes())
        process.waitFor()

        val findings: List<Finding> = result.let {
            val listType: Type = object : TypeToken<ArrayList<Finding?>?>() {}.type
            Gson().fromJson(it, listType)
        } ?: emptyList()

        return findings
    }
}
