package br.com.astrosoft.viewmodel.enderecamento.presenters.mapaDeposito

import br.com.astrosoft.model.enderecamento.domain.Apto

class MapaNiveisAptosModel(model: MapaDepositoViewModel) : MapaModel(model) {
  var apto: Apto? = null
  val mapaEnderecoModel = MapaEnderecoModel(model)
  var ladoSelecionado: LayoutLado? = null
  
  fun atualizaEndereco() = model.exec {
    mapaEnderecoModel.endereco?.let {
      it.refresh()
      mapaEnderecoModel.endereco = it
    }
  }
}