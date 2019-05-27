package br.com.astrosoft.viewmodel.enderecamento.presenters.mapaDeposito

import br.com.astrosoft.model.enderecamento.domain.Endereco

class EnderecoModel(val model: MapaDepositoViewModel) {
  var endereco: Endereco? = null
  val grid = GridEnderecoModel(model)
}