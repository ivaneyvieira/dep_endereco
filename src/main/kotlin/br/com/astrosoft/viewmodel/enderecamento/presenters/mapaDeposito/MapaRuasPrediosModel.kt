package br.com.astrosoft.viewmodel.enderecamento.presenters.mapaDeposito

import br.com.astrosoft.model.enderecamento.domain.Predio

class MapaRuasPrediosModel(model: MapaDepositoViewModel) : MapaModel(model) {
  var layoutRuaPredio: LayoutRuaPredio? = LayoutRuaPredio(model)
  
  fun updateLayoutRuaPredio() {
    layoutRuaPredio = LayoutRuaPredio(model)
  }
  
  fun setLado(predio: Predio) = model.exec {
    layoutRuaPredio?.let { layoutRP ->
      val ladoSelecionado = layoutRP.getLayoutLado(predio)
      model.mapaNiveisAptos.ladoSelecionado = ladoSelecionado
      model.mapaSelecionado = model.mapaNiveisAptos
    }
  }
}