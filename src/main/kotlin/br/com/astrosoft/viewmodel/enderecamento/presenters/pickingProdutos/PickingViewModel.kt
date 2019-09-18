package br.com.astrosoft.viewmodel.enderecamento.presenters.pickingProdutos

import br.com.astrosoft.model.enderecamento.domain.Produto
import br.com.astrosoft.model.enderecamento.domain.RepositorioEndereco
import br.com.astrosoft.model.enderecamento.domain.Saldo
import br.com.astrosoft.model.framework.services.findById
import br.com.astrosoft.viewmodel.framework.viewmodel.ViewModel

class PickingViewModel : ViewModel() {
  var produto: Produto? = null
  val listSaldo = ListSaldoModel(this)
  val listTransferencia = ListTransferenciaModel(this)

  init {
    RepositorioEndereco.updateRegistros()
  }
  
  override fun reloadModel() {
    listSaldo.updateList()
    listTransferencia.updateList()
  }
  
  override fun processaParametros(parameters: Map<String, String?>) {
    parameters["id"]?.let {
      it.toLongOrNull()?.let {idSaldo ->
        val saldo = Saldo.findById(idSaldo)
        produto = saldo?.produto
        listSaldo.itemSelected = saldo
        listSaldo.updateList()
        listTransferencia.updateList()
      }
    }
  }
  
  fun saldoPulmaoTotal(produto: Produto): Double = execNull {
    produto.saldoPulmaoTotal
  } ?: 0.00

  /*25/022019
  fun findBeanValue(text: String): List<Produto> = execList {
    Produto.findProdutoQuery(text)
  }
  */
  
  fun getProdutoQuery(query: String): List<Produto> = execList {
    Produto.findProdutoQuery(query)
  }
  
  fun processaGrids() = exec {
    listSaldo.updateList()
    listTransferencia.updateList()
  }
}
