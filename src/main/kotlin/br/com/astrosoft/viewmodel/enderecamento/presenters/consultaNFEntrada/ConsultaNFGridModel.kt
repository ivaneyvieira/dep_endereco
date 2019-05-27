package br.com.astrosoft.viewmodel.enderecamento.presenters.consultaNFEntrada

import br.com.astrosoft.model.framework.legado.QuerySaci
import br.com.astrosoft.model.framework.legado.beans.NotaEntrada
import br.com.astrosoft.viewmodel.framework.viewmodel.IListModel

class ConsultaNFGridModel(val model: ConsultaNFViewModel) : IListModel<NotaEntrada>() {
  private val saciQuery = QuerySaci()
  override var list: List<NotaEntrada>? = null
  override var itemSelected: NotaEntrada? = null
  
  override fun updateList() {
    model.grid.list = saciQuery.notaEntradaAll(model.header.query)
  }
  
  fun processaNotaEntrada() = model.exec {
    updateList()
  }
}