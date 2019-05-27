package br.com.astrosoft.viewmodel.enderecamento.presenters.mapaDeposito

import br.com.astrosoft.model.enderecamento.domain.Endereco

open class MapaModel(val model: MapaDepositoViewModel) {
  var enderecosSelecionado: List<Endereco>? = null
  
  fun showMapa() = model.exec {
    model.mapaSelecionado = this
  }
}