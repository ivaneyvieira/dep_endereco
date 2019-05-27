package br.com.astrosoft.viewmodel.enderecamento.presenters.enderecamentoProduto

class TransferenciaEntradaModel(val model: EnderecamentoProdutoViewModel) {
  val header = TransferenciaEntradaHeaderModel(model)
  val grid = TransferenciaEntradaGridModel(model)
  
  fun salvaHeader() = model.exec {
    val movProduto = header.movProduto
    movProduto?.let {
      it.quantPalete = header.quantPalete
      it.save()
      model.produtosRecebidosModel.updateList()
    }
  }
}