package br.com.astrosoft.viewmodel.enderecamento.presenters.consultaPulmao

import br.com.astrosoft.model.enderecamento.domain.ETipoNivel.PULMAO
import br.com.astrosoft.model.enderecamento.domain.Produto
import br.com.astrosoft.model.enderecamento.domain.Saldo
import br.com.astrosoft.viewmodel.framework.viewmodel.IListModel
import java.util.*

class GridPulmaoModel(val model: ConsultaPulmaoViewModel) : IListModel<Produto>() {
  override var itemSelected: Produto? = null
  override var list: List<Produto>? = null
  
  override fun updateList() {
    list = model.produtos.orEmpty()
  }
  
  fun processaGrid() = model.exec {
    updateList()
  }
}