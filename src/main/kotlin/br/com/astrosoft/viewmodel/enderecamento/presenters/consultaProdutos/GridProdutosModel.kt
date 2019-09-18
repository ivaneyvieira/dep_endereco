package br.com.astrosoft.viewmodel.enderecamento.presenters.consultaProdutos

import br.com.astrosoft.model.enderecamento.domain.Saldo
import br.com.astrosoft.viewmodel.enderecamento.presenters.consultaPulmao.ConsultaPulmaoViewModel
import br.com.astrosoft.viewmodel.framework.viewmodel.IListModel
import java.util.*

class GridProdutosModel(val model: ConsultaProdutosViewModel) : IListModel<Saldo>() {
  override var itemSelected: Saldo? = null
  override var list: List<Saldo>? = null
  
  override fun updateList() {
    val saldos = ArrayList<Saldo>()
    model.produtos.orEmpty().forEach { produto ->
      saldos.addAll(produto.saldos.orEmpty().filter { saldo ->
        saldo.saldoNConfirmado.toDouble() + saldo.saldoConfirmado.toDouble() > 0
      })
    }
    list = saldos
  }
  
  fun processaGrid() = model.exec {
    updateList()
  }
}