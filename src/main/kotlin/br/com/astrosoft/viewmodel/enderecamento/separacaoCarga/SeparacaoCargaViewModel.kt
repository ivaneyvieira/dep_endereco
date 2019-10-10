package br.com.astrosoft.viewmodel.enderecamento.separacaoCarga

import br.com.astrosoft.viewmodel.framework.viewmodel.ViewModel

class SeparacaoCargaViewModel: ViewModel() {
  val headerModel = HeaderModel(this)
  val gridModel = GridModel(this)

  override fun reloadModel() {
    gridModel.updateList()
  }
}