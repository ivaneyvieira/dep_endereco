package br.com.astrosoft.viewmodel.enderecamento.presenters.enderecamentoProduto

import br.com.astrosoft.model.enderecamento.domain.Produto
import br.com.astrosoft.viewmodel.framework.viewmodel.ViewModel

class EnderecamentoProdutoViewModel : ViewModel() {
  var produto: Produto? = null
  val produtosRecebidosModel = ProdutosRecebidosModel(this)
  
  override fun reloadModel() {
    produtosRecebidosModel.updateList()
    produtosRecebidosModel.transferenciaEntradaModel.header.updateFields()
  }
  
  fun processaGrid() = exec {
    produtosRecebidosModel.updateList()
  }
  
  fun saldoPulmaoTotal(produto: Produto): Double = execNull {
    produto.saldoPulmaoTotal
  } ?: 0.000
  
  fun findBeanValue(text: String): List<Produto> = execList {
    var list: List<Produto> = emptyList()
    exec {
      list = Produto.findProdutoQuery(text)
    }
    list
  }
}
