package br.com.astrosoft.view.framework.vaadin.menu

import com.vaadin.icons.VaadinIcons
import kotlin.annotation.AnnotationRetention.RUNTIME

@Retention(RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class ViewMenuItem(
        val enabled: Boolean = true,
        val grupo: String,
        val icon: VaadinIcons = VaadinIcons.FILE,
        val isStateful: Boolean = true,
        val order: Int = 0,
        val title: String,
        val tags: Array<String> = []
                             )
