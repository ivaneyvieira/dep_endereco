package br.com.astrosoft.viewmodel.enderecamento.presenters.consultaEnderecos

import br.com.astrosoft.model.enderecamento.domain.Saldo
import br.com.astrosoft.viewmodel.framework.viewmodel.IListModel

class GridConsultaEndereco(val model: ConsultaEnderecoViewModel) : IListModel<Saldo>() {
  override var list: List<Saldo>? = null
  override var itemSelected: Saldo? = null
  
  override fun updateList() {
    val saldos = Saldo.findSaldos(model.tipoNivel, model.rua, model.lado, model.predio, model.nivel, model.apto)
    this.list = saldos
  }
  
  fun processaGrid() = model.exec {
    updateList()
  }
}