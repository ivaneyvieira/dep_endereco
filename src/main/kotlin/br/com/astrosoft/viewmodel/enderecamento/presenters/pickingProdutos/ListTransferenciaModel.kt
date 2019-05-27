package br.com.astrosoft.viewmodel.enderecamento.presenters.pickingProdutos

import br.com.astrosoft.model.enderecamento.domain.Transferencia
import br.com.astrosoft.viewmodel.framework.viewmodel.IListModel

class ListTransferenciaModel(val model: PickingViewModel) : IListModel<Transferencia>() {
  override var list: List<Transferencia>? = null
  override var itemSelected: Transferencia? = null
  
  override fun updateList() {
    list = model.produto?.transferenciasPicking
  }
  
  fun savePicking() = model.exec {
    itemSelected?.let {
      it.save()
      it.movProduto?.produto?.recalculaSaldo()
    }
    updateList()
    model.listSaldo.updateList()
  }
}