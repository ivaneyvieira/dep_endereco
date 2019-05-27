package br.com.astrosoft.viewmodel.enderecamento.presenters.mapaDeposito

import br.com.astrosoft.model.enderecamento.domain.Saldo
import br.com.astrosoft.viewmodel.framework.viewmodel.IListModel

class ListEnderecoProduto(val model: MapaDepositoViewModel) : IListModel<Saldo>() {
  override var list: List<Saldo>? = null
  override var itemSelected: Saldo? = null
  
  override fun updateList() {
    model.mapaNiveisAptos.mapaEnderecoModel.endereco?.let {
      list = it.saldos.orEmpty().filter {saldo ->
        saldo.saldoConfirmado.toDouble() != 0.00
        || saldo.saldoNConfirmado.toDouble() != 0.00
      }
    }
  }
  
  fun processaSaldo() = model.exec {
    updateList()
  }
}