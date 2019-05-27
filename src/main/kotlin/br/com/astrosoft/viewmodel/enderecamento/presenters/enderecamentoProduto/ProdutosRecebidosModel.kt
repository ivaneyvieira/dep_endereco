package br.com.astrosoft.viewmodel.enderecamento.presenters.enderecamentoProduto

import br.com.astrosoft.model.enderecamento.domain.MovProduto
import br.com.astrosoft.viewmodel.framework.viewmodel.IListModel

class ProdutosRecebidosModel(val model: EnderecamentoProdutoViewModel) : IListModel<MovProduto>() {
  val transferenciaEntradaModel = TransferenciaEntradaModel(model)
  override var list: List<MovProduto>? = null
  override var itemSelected: MovProduto? = null
  
  override fun updateList() {
    list = MovProduto.movProdutosNaoEnderecados().filter { m ->
      m.quantMov.toDouble() > 0.000 &&
      model.produto?.let { it.id == m.produto?.id } ?: true
    }
  }
  
  fun atualizaDialog(item: MovProduto) = model.exec {
    itemSelected = item
    transferenciaEntradaModel.header.movProduto = item
    transferenciaEntradaModel.header.updateFields()
    transferenciaEntradaModel.grid.updateList()
  }
}