package br.com.astrosoft.viewmodel.enderecamento.presenters.mapaDeposito

import br.com.astrosoft.model.enderecamento.domain.Saldo
import br.com.astrosoft.viewmodel.framework.viewmodel.IListModel

class GridEnderecoModel(val model: MapaDepositoViewModel) : IListModel<Saldo>() {
  override var list: List<Saldo>? = null
  override var itemSelected: Saldo? = null
  
  override fun updateList() {
    list = model.mapaNiveisAptos.mapaEnderecoModel.endereco?.saldos
  }
}