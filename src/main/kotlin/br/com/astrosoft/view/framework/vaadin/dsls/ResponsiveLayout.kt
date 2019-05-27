package br.com.astrosoft.view.framework.vaadin.dsls

import com.jarektoro.responsivelayout.ResponsiveColumn
import com.jarektoro.responsivelayout.ResponsiveLayout
import com.jarektoro.responsivelayout.ResponsiveRow

fun layout(init: ResponsiveLayout.() -> Unit): ResponsiveLayout {
  val layout = ResponsiveLayout()
  layout.init()
  return layout
}

fun ResponsiveLayout.row(init: ResponsiveRow.() -> Unit): ResponsiveRow {
  val row = this.addRow()
  row.init()
  return row
}

fun ResponsiveRow.column(xs: Int, sm: Int, md: Int, lg: Int, init: ResponsiveColumn.() -> Unit): ResponsiveColumn {
  val column = ResponsiveColumn(xs, sm, md, lg)
  addColumn(column)
  column.init()
  return column
}

fun ResponsiveRow.column(xs: Int, sm: Int, md: Int, init: ResponsiveColumn.() -> Unit): ResponsiveColumn {
  return column(xs, sm, md, md, init)
}

fun ResponsiveRow.column(xs: Int, sm: Int, init: ResponsiveColumn.() -> Unit): ResponsiveColumn {
  return column(xs, sm, sm, init)
}

fun ResponsiveRow.column(xs: Int, init: ResponsiveColumn.() -> Unit): ResponsiveColumn {
  return column(xs, xs, init)
}

fun ResponsiveRow.column(init: ResponsiveColumn.() -> Unit): ResponsiveColumn {
  return column(1, init)
}

fun teste() {
  layout {
    row {
      column {}
    }
  }
}