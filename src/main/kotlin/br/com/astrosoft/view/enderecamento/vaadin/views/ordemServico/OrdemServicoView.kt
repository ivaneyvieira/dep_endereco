package br.com.astrosoft.view.enderecamento.vaadin.views.ordemServico

import br.com.astrosoft.view.framework.vaadin.fields.ReportDialog
import br.com.astrosoft.view.framework.vaadin.login.LoginService
import br.com.astrosoft.view.framework.vaadin.menu.ViewMenuItem
import br.com.astrosoft.view.framework.vaadin.views.FormView
import br.com.astrosoft.viewmodel.enderecamento.presenters.ordemServico.OrdemServicoViewModel
import br.com.astrosoft.viewmodel.enderecamento.reports.RelatorioPlacaPicking
import com.github.mvysny.karibudsl.v8.AutoView
import com.vaadin.icons.VaadinIcons
import com.vaadin.ui.Component
import com.vaadin.ui.VerticalLayout

@AutoView("ordemServico")
@ViewMenuItem(title = "Ordens de Serviço",
              icon = VaadinIcons.ARROW_CIRCLE_DOWN,
              grupo = "Movimentação",
              order = 26, tags = ["REC", "EXP"])
class OrdemServicoView : FormView<OrdemServicoViewModel>(OrdemServicoViewModel()) {
  val header = OrdemServicoHeader(model.headerModel)
  val grid = OrdemServicoGrid(model.gridModel)
  
  init {
    title = "Ordens de Serviço"
    buildEditButton(desc = "Placa",
                    icon = VaadinIcons.CLIPBOARD) {
      showReport()
    }
  
    LoginService.currentUser?.let { user ->
      if(user.hasTag("ADM")){
        buildEditButton(desc = "Cancela",
                        icon = VaadinIcons.ERASER) {
          apagaOS()
        }
      }
    }
    
  }
  
  override fun buildContentPanels(): Component {
    val layout = VerticalLayout(header)
    layout.addComponentsAndExpand(grid)
    return layout
  }
  
  override fun updateModel() {
    header.updateModel()
    grid.updateModel()
  }
  
  override fun updateView() {
    header.updateView()
    grid.updateView()
  }
  
  private fun apagaOS() {
    model.removeOs(grid.selectedItems())
  }
  
  private fun showReport() {
    val relatorio = RelatorioPlacaPicking()
    val tranferencias = grid.selectedItems()
    tranferencias.firstOrNull()?.let {
      relatorio.build(it)
      relatorio.fileName?.let {fileName ->
        val dialog = ReportDialog("Placa de Picking", fileName)
        dialog.show()
      }
    }
  }
}