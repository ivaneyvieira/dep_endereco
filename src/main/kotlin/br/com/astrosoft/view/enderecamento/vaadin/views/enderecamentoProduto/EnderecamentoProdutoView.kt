package br.com.astrosoft.view.enderecamento.vaadin.views.enderecamentoProduto

import br.com.astrosoft.model.enderecamento.domain.MovProduto
import br.com.astrosoft.view.enderecamento.vaadin.fields.FieldProduto
import br.com.astrosoft.view.framework.vaadin.menu.ViewMenuItem
import br.com.astrosoft.view.framework.vaadin.views.FormView
import br.com.astrosoft.viewmodel.enderecamento.presenters.enderecamentoProduto.EnderecamentoProdutoViewModel
import com.github.mvysny.karibudsl.v8.AutoView
import com.vaadin.icons.VaadinIcons
import com.vaadin.ui.Component
import com.vaadin.ui.VerticalLayout

@AutoView("entProduto")
@ViewMenuItem(title = "Endereçamento",
              icon = VaadinIcons.LOCATION_ARROW_CIRCLE_O,
              grupo = "Movimentação",
              order = 21, tags = ["REC"])
class EnderecamentoProdutoView : FormView<EnderecamentoProdutoViewModel>(EnderecamentoProdutoViewModel()) {
  private val produtoFilter = FieldProduto().apply {
    saldoPulmaoTotal = model::saldoPulmaoTotal
    findBeanValue = model::findBeanValue
    changeValue = {
      model.produto = it.firstOrNull()
      model.processaGrid()
    }
  }
  
  private val gridProdutosRecebidos = ProdutosRecebidosGrid(model.produtosRecebidosModel)
  
  init {
    title = "Endereçamento de Produtos"
  }
  
  override fun buildContentPanels(): Component {
    val layout = VerticalLayout(produtoFilter)
    layout.addComponentsAndExpand(this.gridProdutosRecebidos)
    return layout
  }
  
  fun setMovimentacaoes(movimentacaoes: List<MovProduto>) {
    this.gridProdutosRecebidos.setValue(movimentacaoes)
  }
  
  override fun updateView() {
    produtoFilter.value = model.produto?.let { listOf(it) }
    gridProdutosRecebidos.updateView()
  }
  
  override fun updateModel() {
    model.produto = produtoFilter.value?.firstOrNull()
    gridProdutosRecebidos.updateModel()
  }
}

