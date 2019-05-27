package br.com.astrosoft.view.enderecamento.vaadin.views.consultaNFEntrada

import br.com.astrosoft.view.framework.vaadin.menu.ViewMenuItem
import br.com.astrosoft.view.framework.vaadin.views.FormView
import br.com.astrosoft.viewmodel.enderecamento.presenters.consultaNFEntrada.ConsultaNFViewModel
import com.github.mvysny.karibudsl.v8.AutoView
import com.vaadin.icons.VaadinIcons
import com.vaadin.ui.Component
import com.vaadin.ui.VerticalLayout

@AutoView("consultaNF")
@ViewMenuItem(title = "NF de Entrada", icon = VaadinIcons.INBOX, grupo = "Consulta",
              order = 30, tags = ["CON", "REC"])
class ConsultaNFView : FormView<ConsultaNFViewModel>(ConsultaNFViewModel()) {
  val header = ConsultaNFHeader(model)
  val grid = ConsultaNFGrid(model.grid)
  
  init {
    title = "Nota Fiscal de Entrada"
  }
  
  override fun updateView() {
    header.updateView()
    grid.updateView()
  }
  
  override fun updateModel() {
    header.updateModel()
    grid.updateModel()
  }
  
  override fun buildContentPanels(): Component {
    val layout = VerticalLayout(header)
    layout.addComponentsAndExpand(grid)
    return layout
  }
}