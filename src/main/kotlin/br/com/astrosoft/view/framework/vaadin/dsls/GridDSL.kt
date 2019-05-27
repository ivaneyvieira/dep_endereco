package br.com.astrosoft.view.framework.vaadin.dsls

import br.com.astrosoft.model.framework.utils.DATE_FORMAT
import br.com.astrosoft.model.framework.utils.INT_FORMAT
import br.com.astrosoft.model.framework.utils.MONEY_FORMAT
import br.com.astrosoft.model.framework.utils.QUANT_FORMAT
import br.com.astrosoft.view.framework.vaadin.Alignment
import br.com.astrosoft.view.framework.vaadin.menu.BeanMenuItem
import br.com.astrosoft.viewmodel.framework.viewmodel.View
import com.vaadin.icons.VaadinIcons
import com.vaadin.ui.Button
import com.vaadin.ui.ComboBox
import com.vaadin.ui.Grid
import com.vaadin.ui.Grid.Column
import com.vaadin.ui.UI
import com.vaadin.ui.renderers.ComponentRenderer
import com.vaadin.ui.renderers.DateRenderer
import com.vaadin.ui.renderers.LocalDateTimeRenderer
import com.vaadin.ui.renderers.NumberRenderer
import com.vaadin.ui.themes.ValoTheme
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import kotlin.reflect.KClass

fun <T> Grid<T>.config(init: Grid<T>.() -> Unit): Grid<T> {
  this.init()
  return this
}

fun <V, T> Grid<T>.column(
        valueProvider: (T) -> V,
        init: Column<T, V>.() -> Unit
                         ): Column<T, V> {
  val addColumn = this.addColumn(valueProvider)
  addColumn.init()
  return addColumn
}

fun <V, T> Grid<T>.columnCombo(
        valueProvider: (T) -> ComboBox<V>,
        init: Column<T, ComboBox<V>>.() -> Unit
                              ): Column<T, ComboBox<V>> {
  val addColumn = this.addColumn(valueProvider, ComponentRenderer())
  addColumn.init()
  return addColumn
}

fun <T> Grid<T>.columnQuant(
        valueProvider: (T) -> BigDecimal,
        init: Column<T, BigDecimal>.() -> Unit
                           ): Column<T, BigDecimal> {
  val addColumn = column(valueProvider, init)
  addColumn.setRenderer(NumberRenderer(QUANT_FORMAT))
  addColumn.setStyleGenerator { Alignment.right }
  return addColumn
}

fun <T> Grid<T>.columnInteger(
        valueProvider: (T) -> Int,
        init: Column<T, Int>.() -> Unit
                             ): Column<T, Int> {
  val addColumn = column(valueProvider, init)
  addColumn.setRenderer(NumberRenderer(INT_FORMAT))
  addColumn.setStyleGenerator { Alignment.right }
  return addColumn
}

fun <T> Grid<T>.columnMoney(
        valueProvider: (T) -> BigDecimal,
        init: Column<T, BigDecimal>.() -> Unit
                           ): Column<T, BigDecimal> {
  val addColumn = column(valueProvider, init)
  addColumn.setRenderer(NumberRenderer(MONEY_FORMAT))
  addColumn.setStyleGenerator { Alignment.right }
  return addColumn
}

fun <T> Grid<T>.columnDate(
        valueProvider: (T) -> Date?,
        init: Column<T, Date?>.() -> Unit
                          ): Column<T, Date?> {
  val addColumn = column(valueProvider, init)
  addColumn.setRenderer(DateRenderer(DATE_FORMAT))
  addColumn.setStyleGenerator { Alignment.left }
  return addColumn
}

fun <T> Grid<T>.columnLocalDate(
        valueProvider: (T) -> LocalDateTime,
        init: Column<T, LocalDateTime>.() -> Unit
                               ): Column<T, LocalDateTime> {
  val addColumn = column(valueProvider, init)
  addColumn.setRenderer(LocalDateTimeRenderer("dd/MM/yyyy HH:mm"))
  addColumn.setStyleGenerator { Alignment.left }
  return addColumn
}

fun <T> Grid<T>.columnButton(
        icon: VaadinIcons,
        actionClick: (T) -> Unit,
        init: Column<T, Button>.() -> Unit
                            ): Grid.Column<T, Button> {
  val addColumn = addComponentColumn { bean ->
    val button = Button(icon)
    button.addClickListener { actionClick(bean) }
    button.addStyleName(ValoTheme.BUTTON_SMALL)
    button
  }
  addColumn.setStyleGenerator { Alignment.center }
  addColumn.init()
  return addColumn
}

fun KClass<out View>.navigatorTo(parameterMap: Map<String, Any?>) {
  val bean = BeanMenuItem(this.java)
  val parameterValue = parameterMap.map { "${it.key}=${it.value}" }
  val ui = UI.getCurrent()
  val navigator = ui?.navigator
  navigator?.navigateTo(bean.id + "/" + parameterValue.joinToString("&"))
}
