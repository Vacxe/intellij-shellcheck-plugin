package io.github.vacxe.shellcheck.intentions

import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.codeInsight.intention.LowPriorityAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import io.github.vacxe.shellcheck.SHELLCHECK
import io.github.vacxe.shellcheck.actions.OpenInBrowserCodeAction

class OpenInBrowserCode : IntentionAction, LowPriorityAction {

    override fun startInWriteAction(): Boolean = true

    override fun getText(): String = "Open documentation"

    override fun getFamilyName(): String = SHELLCHECK

    override fun isAvailable(project: Project, editor: Editor, file: PsiFile): Boolean = true

    override fun invoke(project: Project, editor: Editor?, file: PsiFile?) {
        OpenInBrowserCodeAction().runAction(project, file)
    }
}