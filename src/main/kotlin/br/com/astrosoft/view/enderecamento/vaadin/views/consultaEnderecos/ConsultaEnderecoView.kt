package br.com.astrosoft.view.enderecamento.vaadin.views.consultaEnderecos

import br.com.astrosoft.view.enderecamento.vaadin.views.consultaProdutos.GridConsultaProduto
import br.com.astrosoft.view.framework.vaadin.menu.ViewMenuItem
import br.com.astrosoft.view.framework.vaadin.views.FormView
import br.com.astrosoft.viewmodel.enderecamento.presenters.consultaEnderecos.ConsultaEnderecoViewModel
import com.github.mvysny.karibudsl.v8.AutoView
import com.vaadin.icons.VaadinIcons
import com.vaadin.ui.Component
import com.vaadin.ui.VerticalLayout

@AutoView("consultaEndereco")
@ViewMenuItem(title = "Consulta Endereço",
              icon = VaadinIcons.LOCATION_ARROW_CIRCLE_O,
              grupo = "Consulta", order = 33, tags = ["CON", "REC", "EXP"])
class ConsultaEnderecoView : FormView<ConsultaEnderecoViewModel>(ConsultaEnderecoViewModel()) {
  private val fieldPesquisaEndereco: FieldPesquisaEndereco
  private val gridConsultaPulmao: GridConsultaProduto
  
  init {
    title = "Consulta de Produtos por Endereço"
    fieldPesquisaEndereco = FieldPesquisaEndereco(model)
    gridConsultaPulmao = GridConsultaProduto(model.grid)
  }
  
  override fun buildContentPanels(): Component {
    fieldPesquisaEndereco.setProcessaSaldos { model.grid.processaGrid() }
    val layout = VerticalLayout(fieldPesquisaEndereco)
    layout.addComponentsAndExpand(gridConsultaPulmao)
    return layout
  }
  
  override fun updateView() {
    fieldPesquisaEndereco.updateView()
    gridConsultaPulmao.updateView()
  }
  
  override fun updateModel() {
    gridConsultaPulmao.updateModel()
    fieldPesquisaEndereco.updateModel()
  }
}
