package br.com.astrosoft.viewmodel.enderecamento.presenters.consultaNFEntrada

import br.com.astrosoft.viewmodel.framework.viewmodel.ViewModel

class ConsultaNFViewModel : ViewModel() {
  
  val header = ConsultaNFHeaderModel(this)
  val grid = ConsultaNFGridModel(this)
  
  override fun reloadModel() {
    grid.updateList()
  }
}