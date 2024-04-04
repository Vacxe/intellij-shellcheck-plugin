package io.github.vacxe.shellcheck.utils

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object PluginUtils {
    fun pluginIcon(): Icon = IconLoader.getIcon("META-INF/pluginIconSmall.svg", PluginUtils::class.java.classLoader)

}