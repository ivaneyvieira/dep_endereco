package br.com.astrosoft.view.enderecamento.vaadin.views.mapaDeposito

import br.com.astrosoft.model.enderecamento.domain.Predio
import br.com.astrosoft.model.enderecamento.domain.RepositorioPredio
import br.com.astrosoft.view.enderecamento.vaadin.Styles
import com.vaadin.ui.Alignment
import com.vaadin.ui.CssLayout
import com.vaadin.ui.GridLayout
import com.vaadin.ui.Label
import com.vaadin.ui.Panel
import com.vaadin.ui.themes.ValoTheme

class PanelPredio(val predio: RepositorioPredio, private val selectLado: (RepositorioPredio) -> Unit) : CssLayout() {
  private val panel: Panel
  
  init {
    isResponsive = true
    setSizeFull()
    this.panel = buildPanel()
    addComponent(this.panel)
  }
  
  private fun buildPanel(): Panel {
    val panel = Panel()
    panel.setSizeFull()
    val cell = GridLayout()
    val label = Label(this.predio.predio)

    label.addStyleName(Styles.box_rotate)
    cell.defaultComponentAlignment = Alignment.MIDDLE_CENTER
    cell.addComponent(label)
    cell.setSizeFull()
    panel.content = cell
    panel.addStyleName(ValoTheme.LAYOUT_WELL)
    panel.addStyleName(Styles.box_select)
    panel.addClickListener { selectLado() }
    return panel
  }
  
  private fun selectLado() {
    this.selectLado(this.predio)
  }
  
  fun markPredio() {
    this.panel.removeStyleName(Styles.box_select)
    this.panel.addStyleName(Styles.box_mark)
  }
  
  fun unmarkPredio() {
    this.panel.removeStyleName(Styles.box_mark)
    this.panel.addStyleName(Styles.box_select)
  }
}
