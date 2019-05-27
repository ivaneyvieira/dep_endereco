package br.com.astrosoft.view.enderecamento.vaadin.views.pickingProdutos

import br.com.astrosoft.model.enderecamento.domain.Produto
import br.com.astrosoft.view.enderecamento.vaadin.fields.FieldProduto
import br.com.astrosoft.view.framework.vaadin.menu.ViewMenuItem
import br.com.astrosoft.view.framework.vaadin.views.FormView
import br.com.astrosoft.viewmodel.enderecamento.presenters.pickingProdutos.PickingViewModel
import com.github.mvysny.karibudsl.v8.AutoView
import com.vaadin.icons.VaadinIcons
import com.vaadin.ui.Component
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.VerticalLayout

@AutoView("pickProduto")
@ViewMenuItem(title = "Picking",
              icon = VaadinIcons.ARROW_CIRCLE_DOWN,
              grupo = "Movimentação",
              order = 22, tags = ["EMP", "REC", "EXP"])
class PickingView : FormView<PickingViewModel>(PickingViewModel()) {
  private val fieldProduto: FieldProduto = FieldProduto().apply {
    saldoPulmaoTotal = model::saldoPulmaoTotal
    findBeanValue = model::getProdutoQuery
    changeValue = { atualizaGrid(it) }
  }
  private val gridPickingSaldo = GridPickingSaldo(model.listSaldo).apply {
    caption = "Endereços Pulmão"
  }
  private val gridPickingTransferencia = GridPickingTransferencia(this.model.listTransferencia).apply {
    caption = "Picking Realizados"
  }
  
  init {
    title = "Picking de Produtos"
  }
  
  private fun getProdutoQuery(query: String): List<Produto> {
    return this.model.getProdutoQuery(query)
  }
  
  override fun buildContentPanels(): Component {
    val layout = VerticalLayout(this.fieldProduto)
    val grids = HorizontalLayout()
    grids.addComponentsAndExpand(this.gridPickingSaldo, this.gridPickingTransferencia)
    grids.setExpandRatio(this.gridPickingSaldo, 6f)
    grids.setExpandRatio(this.gridPickingTransferencia, 7f)
    layout.addComponentsAndExpand(grids)
    return layout
  }
  
  private fun atualizaGrid(produtos: List<Produto>) {
    model.produto = produtos.firstOrNull()
    this.model.processaGrids()
  }
  
  override fun updateView() {
    this.fieldProduto.updateField(listOfNotNull(model.produto))
    gridPickingSaldo.updateView()
    gridPickingTransferencia.updateView()
  }
  
  override fun updateModel() {}
}


