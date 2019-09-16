package br.com.astrosoft.viewmodel.enderecamento.presenters.consultaProdutos

import br.com.astrosoft.model.enderecamento.domain.Produto
import br.com.astrosoft.model.enderecamento.domain.RepositorioEndereco
import br.com.astrosoft.viewmodel.framework.viewmodel.ViewModel

class ConsultaProdutosViewModel : ViewModel() {
  var produtos: List<Produto>? = null
  val gridProdutos = GridProdutosModel(this)

  init {
    RepositorioEndereco.updateRegistros()
  }

  override fun reloadModel() {
    gridProdutos.updateList()
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

