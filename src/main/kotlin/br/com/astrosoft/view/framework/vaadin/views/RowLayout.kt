package br.com.astrosoft.view.framework.vaadin.views

import com.jarektoro.responsivelayout.ResponsiveColumn
import com.jarektoro.responsivelayout.ResponsiveLayout
import com.vaadin.ui.Component

class RowLayout : ResponsiveLayout() {
  private val row = addRow()
  
  init {
    this.row.setVerticalSpacing(false)
    this.row.setHorizontalSpacing(true)
    setSizeFull()
  }
  
  override fun addComponent(component: Component, size: Int) {
    val col = ResponsiveColumn(12, size)
    col.component = component
    component.setSizeFull()
    this.row.addColumn(col)
  }
}
