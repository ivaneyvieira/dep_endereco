package br.com.astrosoft.view.framework.vaadin.menu

import com.github.mvysny.karibudsl.v8.AutoView

class BeanMenuItem<V>(val viewClass: Class<V>) {
  private val viewMenuItem: ViewMenuItem = viewClass.getAnnotation(ViewMenuItem::class.java)
  private val autoView: AutoView = viewClass.getAnnotation(AutoView::class.java)
  val enabled = viewMenuItem.enabled
  val grupo = viewMenuItem.grupo
  val icon = viewMenuItem.icon
  val id = autoView.value
  val isStateful = viewMenuItem.isStateful
  val order = viewMenuItem.order
  val title = viewMenuItem.title
  val tags = viewMenuItem.tags
  fun containTag(tag: String) = tags.toList().contains(tag)
}
