package io.github.vacxe.shellcheck.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import io.github.vacxe.shellcheck.utils.PluginUtils
import org.jetbrains.kotlin.idea.KotlinLanguage

class OpenInBrowserCodeAction : AnAction(PluginUtils.pluginIcon()) {

    private val logger = logger<OpenInBrowserCodeAction>()

    override fun update(event: AnActionEvent) {

    }

    override fun actionPerformed(event: AnActionEvent) {

    }

    internal fun runAction(project: Project, psiFile: PsiFile?) {

    }

    override fun getActionUpdateThread() = ActionUpdateThread.EDT
}