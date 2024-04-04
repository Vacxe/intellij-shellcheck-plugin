package io.github.vacxe.shellcheck.config

import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
@State(name = "ShellcheckPluginSettings", storages = [Storage("shellcheck.xml")])
class ShellcheckPluginSettings(
    private val project: Project,
) : SimplePersistentStateComponent<ShellcheckPluginSettings.State>(State()) {

    var arguments: String
        get() = state.arguments.orEmpty()
        set(value) {
            state.arguments = value
        }

    override fun loadState(state: State) {
        super.loadState(state)
    }
    class State : BaseState() {
        var arguments by string()
    }
}