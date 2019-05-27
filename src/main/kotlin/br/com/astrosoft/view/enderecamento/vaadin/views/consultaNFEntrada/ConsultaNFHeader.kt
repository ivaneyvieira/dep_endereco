package br.com.astrosoft.view.enderecamento.vaadin.views.consultaNFEntrada

import br.com.astrosoft.viewmodel.enderecamento.presenters.consultaNFEntrada.ConsultaNFViewModel
import com.vaadin.icons.VaadinIcons
import com.vaadin.shared.ui.MarginInfo
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Panel
import com.vaadin.ui.TextField
import com.vaadin.ui.themes.ValoTheme

class ConsultaNFHeader(val model: ConsultaNFViewModel) : Panel() {
  private val edtQuery = TextField().apply {
    caption = "Pesquisa"
    icon = VaadinIcons.SEARCH
    addValueChangeListener { e ->
      model.header.query = e.value
      model.grid.processaNotaEntrada()
    }
  }
  
  init {
    val layout = HorizontalLayout()
    layout.margin = MarginInfo(false)
    layout.addComponentsAndExpand(edtQuery)
    content = layout
    addStyleName(ValoTheme.PANEL_BORDERLESS)
  }
  
  fun updateView() {
    edtQuery.value = model.header.query
  }
  
  fun updateModel() {
    model.header.query = edtQuery.value
  }
}