package br.com.astrosoft.viewmodel.enderecamento.separacaoCarga

import br.com.astrosoft.model.enderecamento.dtos.Carga
import br.com.astrosoft.viewmodel.framework.viewmodel.IListModel

class GridModel(val model: SeparacaoCargaViewModel): IListModel<Carga>() {
  override var list: List<Carga>? = emptyList()
  override var itemSelected: Carga? = null

  override fun updateList() {
    val numeroCarga = model.headerModel.numeroCarga
    list = Carga.findCarga(numeroCarga)
  }

  fun listUpdate() : List<Carga>{
    updateList()
    list?.forEach { it.initDestino()}
    return list.orEmpty()
  }
}