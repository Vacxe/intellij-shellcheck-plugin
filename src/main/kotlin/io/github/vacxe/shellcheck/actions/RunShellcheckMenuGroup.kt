package io.github.vacxe.shellcheck.actions

import com.intellij.openapi.actionSystem.ActionPlaces
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.fileEditor.FileEditorManager
import io.github.vacxe.shellcheck.utils.PluginUtils

class RunShellcheckMenuGroup: DefaultActionGroup(null, null, PluginUtils.pluginIcon()) {
    override fun update(e: AnActionEvent) {
        val project = e.project
        val presentation = e.presentation
        if (project == null) {
            presentation.isEnabled = false
            presentation.isVisible = false
            return
        } else {
            presentation.isVisible = true
        }
        if (e.place == ActionPlaces.MAIN_MENU) {
            val selectedFiles = FileEditorManager.getInstance(project).selectedFiles
            presentation.isEnabled = selectedFiles.isNotEmpty()
        }
    }
}
